package com.beerjournal.datamodel.repository;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Optional;

import com.beerjournal.datamodel.entity.UserCollectionEntity;

public interface UserCollectionRepository extends Repository<UserCollectionEntity> {
	Collection<UserCollectionEntity> getAllCollections();
	Collection<UserCollectionEntity> getUserCollections(String userId);
	UserCollectionEntity getById(String id);
	Optional<BufferedImage> getImageForUserCollection(String userCollectionId);
	void deleteWholeCollection(UserCollectionEntity entity);
}
