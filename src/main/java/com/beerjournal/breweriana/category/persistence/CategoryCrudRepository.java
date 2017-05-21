package com.beerjournal.breweriana.category.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface CategoryCrudRepository extends MongoRepository<Category, ObjectId> {

    Optional<Category> findOneByName(String name);

}
