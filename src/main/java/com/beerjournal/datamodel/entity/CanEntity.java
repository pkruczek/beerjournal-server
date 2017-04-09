package com.beerjournal.datamodel.entity;

import java.util.Optional;

import com.beerjournal.datamodel.model.Can;
import com.beerjournal.datamodel.model.CollectableObject;

public class CanEntity extends CollectableObjectEntity {
	private double volume;

	/**
	 * 
	 * @param id - object identifier on data base
	 * @param ownerID - identifier of owner
	 * @param brewery - name of brewery
	 * @param volume - volume of can
	 */
	public CanEntity(String id, String ownerID, String brewery, double volume) {
		this.id = Optional.of(id);
		this.ownerID = ownerID;
		this.brewery = brewery;
		this.volume = volume;
	}
	
	/**
	 * 
	 * @param ownerID - identifier of owner
	 * @param brewery - name of brewery
	 * @param volume - volume of can
	 */
	public CanEntity(String ownerID, String brewery, double volume) {
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
		return "CanEntity [volume=" + volume + ", id=" + id + ", brewery=" + brewery + ", ownerID=" + ownerID + "]";
	}

	@Override
	public CollectableObject getObject() {
		return new Can(ownerID, brewery, volume);
	}
}
