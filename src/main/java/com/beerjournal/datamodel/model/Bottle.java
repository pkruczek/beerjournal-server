package com.beerjournal.datamodel.model;

import com.beerjournal.datamodel.entity.BottleEntity;
import com.beerjournal.datamodel.entity.CollectableObjectEntity;

public class Bottle extends CollectableObject{
	public double volume;
	
	public Bottle() {
	}

	public Bottle(String ownerID, String brewery, double volume) {
		this.ownerID = ownerID;
		this.brewery = brewery;
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Bottle [volume=" + volume + ", id=" + id + ", brewery=" + brewery + "]";
	}

	@Override
	public CollectableObjectEntity getEntity() {
		return new BottleEntity(id, ownerID, brewery, volume);
	}

	@Override
	public Bottle update(CollectableObjectEntity entity) {
		super.update(entity);
		
		BottleEntity bottle = (BottleEntity)entity;
		volume = bottle.getVolume();
		
		return this;
	}
}
