package com.beerjournal.datamodel.repository.mongo.access;

import com.beerjournal.datamodel.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface UserMongoRepository extends MongoRepository<User, String> {
    Collection<User> findByUsername(String username);

    User findById(String id);
}
