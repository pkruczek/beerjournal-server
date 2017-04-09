package com.beerjournal.datamodel.model;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.LabelEntity;

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

	@Override
	public CollectableObjectEntity getEntity() {
		return new LabelEntity(id, ownerID, brewery);
	}

}
