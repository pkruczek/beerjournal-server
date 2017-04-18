package com.beerjournal.breweriana.persistence.item;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface ItemCrudRepository extends MongoRepository<Item, ObjectId> {
    Optional<Item> findOneByName(String name);
}
