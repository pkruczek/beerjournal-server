package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.persistence.ExchangeRepository;
import com.beerjournal.breweriana.exchange.persistence.ExchangeState;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import static com.beerjournal.breweriana.utils.Converters.toObjectId;
import static com.beerjournal.breweriana.utils.Converters.toOfferState;

@Service
@RequiredArgsConstructor
class ExchangeStateService {

    private final ItemRepository itemRepository;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeUpdatePermissionService exchangeUpdatePermissionService;

    ExchangeStateDto updateState(String exchangeId, ExchangeStateDto exchangeStateDto) {
        ExchangeOffer exchangeOffer = findExchangeOrThrow(toObjectId(exchangeId));
        ExchangeState oldState = exchangeOffer.getState();
        ExchangeState newState = toOfferState(exchangeStateDto.getState());

        validateStateChange(oldState, newState);
        exchangeUpdatePermissionService.validateExchangeStatusUpdater(exchangeOffer, newState);

        if(newState == ExchangeState.COMPLETED) {
            performExchange(exchangeOffer);
        }

        updateState(exchangeOffer, newState);

        return exchangeStateDto;
    }

    private void validateStateChange(ExchangeState oldState, ExchangeState newState) {
        if(!oldState.canChangeTo(newState)) {
            throw new BeerJournalException(ErrorInfo.EXCHANGE_BAD_NEXT_STATE);
        }
    }

    private void updateState(ExchangeOffer exchangeOffer, ExchangeState state) {
        exchangeRepository.save(exchangeOffer
                .withState(state));
    }

    private void changeOwner(ObjectId itemId, ObjectId newOwnerId) {
        Item item = itemRepository.deleteLeavingImages(itemId);
        itemRepository.save(item
                .withId(null)
                .withOwnerId(newOwnerId));
    }

    private void performExchange(ExchangeOffer exchangeOffer) {
        exchangeOffer.getOfferedItems()
                .forEach(item -> changeOwner(item.getItemId(), exchangeOffer.getOwnerId()));
        exchangeOffer.getDesiredItems()
                .forEach(item -> changeOwner(item.getItemId(), exchangeOffer.getOfferorId()));
    }

    private ExchangeOffer findExchangeOrThrow(ObjectId exchangeId) {
        return exchangeRepository.findOneById(exchangeId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.EXCHANGE_NOT_FOUND));
    }
}
