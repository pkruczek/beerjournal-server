package com.beerjournal.breweriana.persistence.item;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ItemCrudRepository extends MongoRepository<Item, ObjectId> {
    Optional<Item> findOneByName(String name);
    Set<Item> findByNameNotIn(Collection itemsNames);
}
