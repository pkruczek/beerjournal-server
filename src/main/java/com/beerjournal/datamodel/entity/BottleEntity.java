package com.beerjournal.datamodel.entity;

import java.util.Optional;

import com.beerjournal.datamodel.model.Bottle;
import com.beerjournal.datamodel.model.CollectableObject;

public class BottleEntity extends CollectableObjectEntity{
	private double volume;

	/**
	 * 
	 * @param id - object identifier on data base
	 * @param ownerID - identifier of owner
	 * @param brewery - name of brewery
	 * @param volume - volume of bottle
	 */
	public BottleEntity(String id, String ownerID, String brewery, double volume) {
		this.id = Optional.of(id);
		this.ownerID = ownerID;
		this.brewery = brewery;
		this.volume = volume;
	}
	
	/**
	 * 
	 * @param ownerID - identifier of owner
	 * @param brewery - name of brewery
	 * @param volume - volume of bottle
	 */
	public BottleEntity(String ownerID, String brewery, double volume) {
		this.id = Optional.empty();
		this.ownerID = ownerID;
		this.brewery = brewery;
		this.volume = volume;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "BottleEntity [volume=" + volume + ", id=" + id + ", brewery=" + brewery + ", ownerID=" + ownerID + "]";
	}

	@Override
	public CollectableObject getObject() {
		return new Bottle(ownerID, brewery, volume);
	}
}
