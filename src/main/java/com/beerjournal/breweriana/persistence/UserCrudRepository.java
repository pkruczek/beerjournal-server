package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserCrudRepository extends MongoRepository<User, ObjectId>{
    Optional<User> findOneById(ObjectId id);
    Optional<User> findOneByEmail(String email);
    Optional<User> deleteOneById(ObjectId id);
}
