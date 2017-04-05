package com.beerjournal.datamodel.repository;

import java.util.Collection;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.model.CollectableObject;

public interface CollectableObjectsRepository extends Repository<CollectableObject, CollectableObjectEntity> {
	public Collection<CollectableObjectEntity> getByBrewery(String brewery);
	public CollectableObjectEntity getById(String id);
	public CollectableObject getObjectById(String id);
}
