package com.beerjournal.breweriana.persistence.collection;


import org.springframework.data.mongodb.repository.MongoRepository;

interface UserCollectionCrudRepository extends MongoRepository<UserCollection, String> {
}
