package com.beerjournal.breweriana.persistence.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId>{
    Optional<User> findOneById(ObjectId id);
}
