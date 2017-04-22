package com.beerjournal.breweriana.persistence;


import com.beerjournal.breweriana.persistence.collection.UserCollection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserCollectionCrudRepository extends MongoRepository<UserCollection, ObjectId> {
    Optional<UserCollection> findOneById(ObjectId id);
    Optional<UserCollection> findOneByOwnerId(ObjectId userId);
}
