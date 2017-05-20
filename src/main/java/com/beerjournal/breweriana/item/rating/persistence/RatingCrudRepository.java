package com.beerjournal.breweriana.item.rating.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RatingCrudRepository extends MongoRepository<Rating, ObjectId> {
    Optional<Rating> findOneById(ObjectId id);
}
