package com.beerjournal.datamodel.repository;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;

public interface CollectableObjectsRepository extends Repository<CollectableObjectEntity> {
	public Collection<CollectableObjectEntity> getByBrewery(String brewery);
	public Collection<CollectableObjectEntity> getAllObjectsForUser(String userID);
	public CollectableObjectEntity getById(String id);
	public void saveImageForObject(CollectableObjectEntity object, InputStream image);
	public Optional<BufferedImage> getImageForObject(String objectID);
}
