package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.persistence.ExchangeRepository;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.beerjournal.breweriana.utils.Converters.*;

@Service
@RequiredArgsConstructor
class ExchangeFindService {

    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    ExchangeOfferDetailsDto findExchangeById(String id) {
        ExchangeOffer exchangeOffer = exchangeRepository.findOneById(toObjectId(id))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.EXCHANGE_NOT_FOUND));
        return ExchangeOfferDetailsDto.of(exchangeOffer);
    }

    Set<ExchangeOfferDetailsDto> findExchangesByOfferor(String offerorStringId) {
        ObjectId ownerId = toObjectId(offerorStringId);
        findUserOrThrow(ownerId);
        return exchangeRepository.findAllByOfferorId(toObjectId(offerorStringId))
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    Set<ExchangeOfferDetailsDto> findExchangesByOwner(String ownerStringId) {
        ObjectId ownerId = toObjectId(ownerStringId);
        findUserOrThrow(ownerId);
        return exchangeRepository.findAllByOwnerId(ownerId)
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    Set<ExchangeOfferDetailsDto> findExchangesByOfferorAndDesiredItemId(String offerorId, String itemId) {
        return exchangeRepository.findAllByOfferorIdAndDesiredItemId(toObjectId(offerorId), toObjectId(itemId))
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    Set<ExchangeOfferDetailsDto> findSimilarExchanges(String offerorId, String ownerId, List<String> offeredItemIds,
                                                      List<String> desiredItemIds) {
        Stream<ExchangeOffer> exchanges = exchangeRepository.findMatchingExchange(
                toObjectId(offerorId),
                toObjectId(ownerId),
                toObjectIds(desiredItemIds).collect(Collectors.toSet()),
                toObjectIds(offeredItemIds).collect(Collectors.toSet()));

        return exchanges
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    Set<ExchangeOfferDetailsDto> findExchangesByOfferorAndState(String offerorId, String state) {
        return exchangeRepository.findAllByOfferorAndState(toObjectId(offerorId), toOfferState(state))
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    Set<ExchangeOfferDetailsDto> findExchangesByOwnerAndState(String ownerId, String state) {
        return exchangeRepository.findAllByOwnerAndState(toObjectId(ownerId), toOfferState(state))
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }


    private User findUserOrThrow(ObjectId userId) {
        return userRepository.findOneById(userId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_NOT_FOUND));
    }

}
