package com.beerjournal.breweriana.user.persistence;

import com.beerjournal.breweriana.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserCrudRepository extends MongoRepository<User, ObjectId>{
    Optional<User> findOneById(ObjectId id);
    Optional<User> findOneByEmail(String email);
}
