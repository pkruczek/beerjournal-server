package com.beerjournal.breweriana.item.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

interface ItemCrudRepository extends MongoRepository<Item, ObjectId> {
    Optional<Item> findOneById(ObjectId id);
    Optional<Item> findOneByName(String name);
    Set<Item> findByNameNotIn(Collection itemsNames);
}
