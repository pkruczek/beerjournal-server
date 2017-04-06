package com.beerjournal.datamodel.repository.mongo.access;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.beerjournal.datamodel.model.CollectableObject;

public interface CollectableObjectsMongoRepository extends MongoRepository<CollectableObject, String> {
	public Collection<CollectableObject> findByBrewery(String brewery);
	public Collection<CollectableObject> findByOwnerID(String ownerID);
	public CollectableObject findById(String id);
}
