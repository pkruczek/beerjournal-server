package com.beerjournal.breweriana.user.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserCrudRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findOneById(ObjectId id);
    Optional<User> findOneByEmail(String email);
    Page<User> findByFirstNameStartsWithAndLastNameStartsWith(String firstName, String lastName, Pageable pageable);
}
