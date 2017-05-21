package com.beerjournal.breweriana.exchange.persitence;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ExchangeRepository {

    private final ExchangeCrudRepository crudRepository;

    public Optional<ExchangeItemOffer> findOneById(ObjectId id) {
        return crudRepository.findOneById(id);
    }

    public ExchangeItemOffer save(ExchangeItemOffer exchangeItemOffer) {
        return crudRepository.save(exchangeItemOffer);
    }

    public Stream<ExchangeItemOffer> findAllByOwnerId(ObjectId ownerId) {
        return crudRepository.findAllByOwnerId(ownerId);
    }

    public Stream<ExchangeItemOffer> findAllByOfferorId(ObjectId ownerId) {
        return crudRepository.findAllByOfferorId(ownerId);
    }

}
