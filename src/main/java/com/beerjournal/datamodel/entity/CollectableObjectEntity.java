package com.beerjournal.datamodel.entity;

import java.util.Optional;

import com.beerjournal.datamodel.model.CollectableObject;

public abstract class CollectableObjectEntity {
	protected Optional<String> id = Optional.empty();
	protected String brewery;
	protected String ownerID;
	
	public abstract CollectableObject getObject();
	
	public String getBrewery() {
		return brewery;
	}
	public void setBrewery(String brewery) {
		this.brewery = brewery;
	}
	
	public String getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}
	
	/**
	 * 
	 * @return data base identifier if object is already persisted
	 */
	public Optional<String> getId() {
		return id;
	}
	
	/**
	 * DO NOT POPULATE - id is automatically set during persisting process
	 * @param id - object identifier on data base
	 */
	public void setId(String id) {
		this.id = Optional.of(id);
	}
}
