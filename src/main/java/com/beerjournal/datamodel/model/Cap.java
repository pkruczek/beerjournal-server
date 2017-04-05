package com.beerjournal.datamodel.model;

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
}
