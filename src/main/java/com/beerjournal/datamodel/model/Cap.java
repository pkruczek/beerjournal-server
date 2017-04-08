package com.beerjournal.datamodel.model;

import com.beerjournal.datamodel.entity.CapEntity;
import com.beerjournal.datamodel.entity.CollectableObjectEntity;

public class Cap extends CollectableObject{
	
	public Cap() {
	}
	
	public Cap(String ownerID, String brewery) {
		this.ownerID = ownerID;
		this.brewery = brewery;
	}

	@Override
	public String toString() {
		return "Cap [id=" + id + ", brewery=" + brewery + "]";
	}

	@Override
	public CollectableObjectEntity getEntity() {
		return new CapEntity(id, ownerID, brewery);
	}
}
