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

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.breweriana.utils.Converters.toObjectId;

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

    Set<ExchangeOfferDetailsDto> findExchangesByOwnerId(String ownerStringId) {
        ObjectId ownerId = toObjectId(ownerStringId);
        findUserOrThrow(ownerId);
        return exchangeRepository.findAllByOwnerId(ownerId)
                .map(ExchangeOfferDetailsDto::of)
                .collect(Collectors.toSet());
    }

    private User findUserOrThrow(ObjectId userId) {
        return userRepository.findOneById(userId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_NOT_FOUND));
    }

}
