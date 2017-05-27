package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import com.beerjournal.breweriana.collection.persistence.UserCollection;
import com.beerjournal.breweriana.collection.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.persistence.ExchangeRepository;
import com.beerjournal.breweriana.exchange.persistence.ExchangeState;
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
public class ExchangeUpdateService {

    private final ExchangeRepository exchangeRepository;
    private final UserCollectionRepository userCollectionRepository;
    private final ExchangeUpdatePermissionService exchangeUpdatePermissionService;

    ExchangeOfferDetailsDto createExchange(ExchangeOfferCreateDto createDto) {
        ExchangeOffer exchangeOffer = exchangeRepository.save(toExchangeItemOffer(createDto));
        return ExchangeOfferDetailsDto.of(exchangeOffer);
    }

    ExchangeOfferDetailsDto updateExchange(String exchangeId, ExchangeUpdateDto updateDto) {
        ExchangeOffer exchangeOffer = findExchangeOrThrow(exchangeId);

        validateModifiability(exchangeOffer.getState());
        exchangeUpdatePermissionService.validateExchangeUpdater(exchangeOffer);

        ExchangeOffer updatedOffer = exchangeRepository.save(exchangeOffer
                .withDesiredItems(findItemsOrThrow(updateDto.getDesiredItemIds(), exchangeOffer.getOwnerId()))
                .withOfferedItems(findItemsOrThrow(updateDto.getOfferedItemIds(), exchangeOffer.getOfferorId())));

        return ExchangeOfferDetailsDto.of(updatedOffer);
    }

    private void validateModifiability(ExchangeState state) {
        if(!state.isModifiable()) {
            throw new BeerJournalException(ErrorInfo.EXCHANGE_NOT_MODIFIABLE);
        }
    }

    private ExchangeOffer findExchangeOrThrow(String exchangeId) {
        return exchangeRepository.findOneById(toObjectId(exchangeId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.EXCHANGE_NOT_FOUND));
    }

    private ExchangeOffer toExchangeItemOffer(ExchangeOfferCreateDto createDto) {
        return ExchangeOffer.builder()
                .offerorId(toObjectId(createDto.getOfferorId()))
                .ownerId(toObjectId(createDto.getOwnerId()))
                .desiredItems(findItemsOrThrow(createDto.getDesiredItemIds(), createDto.getOwnerId()))
                .offeredItems(findItemsOrThrow(createDto.getOfferedItemIds(), createDto.getOfferorId()))
                .state(ExchangeState.WAITING_FOR_OWNER)
                .build();

    }

    private Set<ItemRef> findItemsOrThrow(Set<String> itemIds, String ownerId) {
        return findItemsOrThrow(itemIds, toObjectId(ownerId));
    }

    private Set<ItemRef> findItemsOrThrow(Set<String> itemIds, ObjectId ownerId) {
        UserCollection userCollection = userCollectionRepository.findOneByOwnerId(ownerId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_COLLECTION_NOT_FOUND));

        Set<ItemRef> itemRefs = userCollection.getItemRefs()
                .stream()
                .filter(ref -> itemIds.contains(toStringId(ref.getItemId())))
                .collect(Collectors.toSet());

        if (itemRefs.size() != itemIds.size()) {
            throw new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND);
        }

        return itemRefs;
    }

}
