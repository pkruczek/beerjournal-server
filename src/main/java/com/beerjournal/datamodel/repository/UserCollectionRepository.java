package com.beerjournal.datamodel.repository;

import java.util.Collection;

import com.beerjournal.datamodel.entity.UserCollectionEntity;

public interface UserCollectionRepository extends Repository<UserCollectionEntity> {
	Collection<UserCollectionEntity> getAllCollections();
	Collection<UserCollectionEntity> getUserCollections(String userId);
	UserCollectionEntity getById(String id);
	void deleteWholeCollection(UserCollectionEntity entity);
}
