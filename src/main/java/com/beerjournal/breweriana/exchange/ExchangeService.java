package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import com.beerjournal.breweriana.collection.persistence.UserCollection;
import com.beerjournal.breweriana.collection.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.exchange.persitence.ExchangeItemOffer;
import com.beerjournal.breweriana.exchange.persitence.ExchangeRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.breweriana.utils.Converters.toObjectId;
import static com.beerjournal.breweriana.utils.Converters.toStringId;

@Service
@RequiredArgsConstructor
class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final UserCollectionRepository userCollectionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    ExchangeItemOfferDetailsDto findExchangeById(String id) {
        ExchangeItemOffer exchangeItemOffer = exchangeRepository.findOneById(toObjectId(id))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.EXCHANGE_NOT_FOUND));
        return ExchangeItemOfferDetailsDto.of(exchangeItemOffer);
    }

    ExchangeItemOfferDetailsDto createExchange(ExchangeItemOfferCreateDto createDto) {
        ExchangeItemOffer exchangeItemOffer = exchangeRepository.save(toExchangeItemOffer(createDto));
        return ExchangeItemOfferDetailsDto.of(exchangeItemOffer);
    }

    Set<ExchangeItemOfferDetailsDto> findExchangesByOfferor(String offerorStringId) {
        ObjectId ownerId = toObjectId(offerorStringId);
        findUserOrThrow(ownerId);
        return exchangeRepository.findAllByOfferorId(toObjectId(offerorStringId))
                .map(ExchangeItemOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    Set<ExchangeItemOfferDetailsDto> findExchangesByOwnerId(String ownerStringId) {
        ObjectId ownerId = toObjectId(ownerStringId);
        findUserOrThrow(ownerId);
        return exchangeRepository.findAllByOwnerId(ownerId)
                .map(ExchangeItemOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    ExchangeStatusDto updateStatus(String exchangeId, ExchangeStatusDto exchangeStatusDto) {
        ExchangeItemOffer exchangeItemOffer = findExchangeOrThrow(toObjectId(exchangeId));

        verifyOwner(exchangeItemOffer.getOwnerId());

        if(exchangeStatusDto.isPerformed() && !exchangeItemOffer.isPerformed()) {
            performExchange(exchangeItemOffer);
        }

        return exchangeStatusDto;
    }

    private void markAsPerformed(ExchangeItemOffer exchangeItemOffer) {
        exchangeRepository.save(exchangeItemOffer
                .withPerformed(true));
    }

    private ExchangeItemOffer findExchangeOrThrow(ObjectId exchangeId) {
        return exchangeRepository.findOneById(exchangeId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.EXCHANGE_NOT_FOUND));
    }

    private void performExchange(ExchangeItemOffer exchangeItemOffer) {
        exchangeItemOffer.getOfferedItems()
                .forEach(item -> changeOwner(item.getItemId(), exchangeItemOffer.getOwnerId()));
        changeOwner(exchangeItemOffer.getDesiredItem().getItemId(), exchangeItemOffer.getOfferorId());
        markAsPerformed(exchangeItemOffer);
    }

    private User findUserOrThrow(ObjectId userId) {
        return userRepository.findOneById(userId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_NOT_FOUND));
    }

    private void verifyOwner(ObjectId ownerId) {
        if(!securityUtils.checkIfAuthorized(ownerId)) {
            throw new BeerJournalException(ErrorInfo.EXCHANGE_FORBIDDEN_MODIFICATION);
        }
    }

    private void changeOwner(ObjectId itemId, ObjectId newOwnerId) {
        Item item = itemRepository.deleteLeavingImages(itemId);
        itemRepository.save(item
                .withId(null)
                .withOwnerId(newOwnerId));
    }

    private ExchangeItemOffer toExchangeItemOffer(ExchangeItemOfferCreateDto createDto) {
        return ExchangeItemOffer.builder()
                .offerorId(toObjectId(createDto.getOfferorId()))
                .ownerId(toObjectId(createDto.getOwnerId()))
                .desiredItem(findItemOrThrow(createDto.getDesiredItemId()))
                .offeredItems(findOfferedItemsOrThrow(createDto.getOfferedItemIds(), createDto.getOfferorId()))
                .performed(false)
                .build();

    }

    private Set<ItemRef> findOfferedItemsOrThrow(Set<String> offeredItemIds, String offerorId) {
        UserCollection userCollection = userCollectionRepository.findOneByOwnerId(toObjectId(offerorId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_COLLECTION_NOT_FOUND));

        Set<ItemRef> itemRefs = userCollection.getItemRefs()
                .stream()
                .filter(ref -> offeredItemIds.contains(toStringId(ref.getItemId())))
                .collect(Collectors.toSet());

        if (itemRefs.size() != offeredItemIds.size()) {
            throw new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND);
        }

        return itemRefs;
    }

    private ItemRef findItemOrThrow(String itemId) {
        return itemRepository.findOneById(toObjectId(itemId))
                .map(Item::asItemRef)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }

}
