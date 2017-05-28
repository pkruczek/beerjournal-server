package com.beerjournal.breweriana.exchange.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.stream.Stream;

interface ExchangeCrudRepository extends MongoRepository<ExchangeOffer, ObjectId> {

    Optional<ExchangeOffer> findOneById(ObjectId id);

    Stream<ExchangeOffer> findAllByOwnerId(ObjectId ownerId);

    Stream<ExchangeOffer> findAllByOfferorId(ObjectId offerorId);

}
