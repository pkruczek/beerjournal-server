package com.beerjournal.breweriana.persistence.category;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryCrudRepository extends MongoRepository<Category, ObjectId> {
    Optional<Category> findOneByName(String name);
}
