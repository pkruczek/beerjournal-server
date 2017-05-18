package com.beerjournal.breweriana.exchange.persitence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.stream.Stream;

interface ExchangeCrudRepository extends MongoRepository<ExchangeItemOffer, ObjectId> {

    Optional<ExchangeItemOffer> findOneById(ObjectId id);

    Stream<ExchangeItemOffer> findAllByOwnerId(ObjectId ownerId);

    Stream<ExchangeItemOffer> findAllByOfferorId(ObjectId offerorId);

}
