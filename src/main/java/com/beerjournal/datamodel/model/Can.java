package com.beerjournal.datamodel.model;

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
}
