package com.beerjournal.datamodel.model;

import org.springframework.data.annotation.Id;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;

public abstract class CollectableObject {
	
	@Id
	public String id;
	
	public String brewery;
	public String ownerID;
	
	public abstract CollectableObjectEntity getEntity();
	
	public CollectableObject update(CollectableObjectEntity entity) {
		brewery = entity.getBrewery();
		ownerID = entity.getOwnerID();
		return this;
	}
}
