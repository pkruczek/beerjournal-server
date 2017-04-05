package com.beerjournal.datamodel.model;

public class Label extends CollectableObject {
	
	public Label() {
	}
	
	public Label(String ownerID, String brewery) {
		this.ownerID = ownerID;
		this.brewery = brewery;
	}

	@Override
	public String toString() {
		return "Label [id=" + id + ", brewery=" + brewery + "]";
	}

}
