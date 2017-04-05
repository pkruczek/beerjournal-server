package com.beerjournal.datamodel.repository;

import java.util.Collection;

import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.model.UserCollection;

public interface UserCollectionRepository extends Repository<UserCollection, UserCollectionEntity> {
	public Collection<UserCollectionEntity> getAllCollections();
	public Collection<UserCollectionEntity> getUserCollections(String userId);
	public UserCollectionEntity getById(String id);
	public void deleteWholeCollection(UserCollectionEntity entity);
}
