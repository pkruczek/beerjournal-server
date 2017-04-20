package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.item.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

interface ItemCrudRepository extends MongoRepository<Item, ObjectId> {
    Optional<Item> findOneByName(String name);
    Set<Item> findByNameNotIn(Collection itemsNames);
}
