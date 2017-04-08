package com.beerjournal.datamodel.repository;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;

public interface CollectableObjectsRepository extends Repository<CollectableObjectEntity> {
	Collection<CollectableObjectEntity> getByBrewery(String brewery);
	Collection<CollectableObjectEntity> getAllObjectsForUser(String userID);
	CollectableObjectEntity getById(String id);
	void saveImageForObject(CollectableObjectEntity object, InputStream image);
	Optional<BufferedImage> getImageForObject(String objectID);
}
