package com.beerjournal.breweriana.exchange.persistence;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ExchangeRepository {

    private final ExchangeCrudRepository crudRepository;

    public Optional<ExchangeOffer> findOneById(ObjectId id) {
        return crudRepository.findOneById(id);
    }

    public ExchangeOffer save(ExchangeOffer exchangeOffer) {
        return crudRepository.save(exchangeOffer);
    }

    public Stream<ExchangeOffer> findAllByOwnerId(ObjectId ownerId) {
        return crudRepository.findAllByOwnerId(ownerId);
    }

    public Stream<ExchangeOffer> findAllByOfferorId(ObjectId ownerId) {
        return crudRepository.findAllByOfferorId(ownerId);
    }

}
