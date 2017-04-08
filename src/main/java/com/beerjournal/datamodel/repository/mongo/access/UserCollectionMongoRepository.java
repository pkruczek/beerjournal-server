package com.beerjournal.datamodel.repository.mongo.access;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.beerjournal.datamodel.model.UserCollection;

public interface UserCollectionMongoRepository extends MongoRepository<UserCollection, String> {
	public Collection<UserCollection> findByUserID(String userId);
	public UserCollection findById(String id);
}
