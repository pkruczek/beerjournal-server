package com.beerjournal.datamodel.model;

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
}
