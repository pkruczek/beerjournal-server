package com.beerjournal.datamodel.model;

import org.springframework.data.annotation.Id;

public abstract class CollectableObject {
	
	@Id
	public String id;
	
	public String brewery;
	public String ownerID;
}
