package com.beerjournal.datamodel.entity;

import java.util.Optional;

public class CapEntity extends CollectableObjectEntity{
	
	/**
	 * 
	 * @param id - object identifier on data base
	 * @param ownerID - identifier of owner
	 * @param brewery - name of brewery
	 */
	public CapEntity(String id, String ownerID, String brewery) {
		this.id = Optional.of(id);
		this.ownerID = ownerID;
		this.brewery = brewery;
	}

	/**
	 * 
	 * @param ownerID - identifier of owner
	 * @param brewery - name of brewery
	 */
	public CapEntity(String ownerID, String brewery) {
		this.id = Optional.empty();
		this.ownerID = ownerID;
		this.brewery = brewery;
	}

	@Override
	public String toString() {
		return "CapEntity [id=" + id + ", brewery=" + brewery + ", ownerID=" + ownerID + "]";
	}
}
