package com.beerjournal.datamodel.model;

import com.beerjournal.datamodel.entity.CanEntity;
import com.beerjournal.datamodel.entity.CollectableObjectEntity;

public class Can extends CollectableObject {
	public double volume;
	
	public Can() {
	}

	public Can(String ownerID, String brewery, double volume) {
		this.ownerID = ownerID;
		this.brewery = brewery;
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Can [volume=" + volume + ", id=" + id + ", brewery=" + brewery + "]";
	}

	@Override
	public CollectableObjectEntity getEntity() {
		return new CanEntity(id, ownerID, brewery, volume);
	}
	
	@Override
	public Can update(CollectableObjectEntity entity) {
		super.update(entity);
		
		CanEntity can = (CanEntity)entity;
		volume = can.getVolume();
		
		return this;
	}
}
