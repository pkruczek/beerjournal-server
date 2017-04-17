package com.beerjournal.breweriana.persistence.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findOneByName(String name);
}
