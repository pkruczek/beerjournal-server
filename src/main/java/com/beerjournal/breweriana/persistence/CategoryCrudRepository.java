package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.category.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface CategoryCrudRepository extends MongoRepository<Category, ObjectId> {
    Optional<Category> findOneByName(String name);
}
