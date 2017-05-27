package com.beerjournal.breweriana.item.rating.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

public interface RatingCrudRepository extends MongoRepository<Rating, ObjectId> {
    Optional<Rating> findOneById(ObjectId id);

    Set<Rating> findAllByItemId(ObjectId id);

    Set<Rating> findAllByRaterId(ObjectId id);

    Optional<Rating> findOneByItemIdAndRaterId(ObjectId itemId, ObjectId raterId);
}
