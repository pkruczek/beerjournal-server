package com.beerjournal.breweriana.persistence.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findOneById(String id);
}
