package com.beerjournal.datamodel.model;

import java.util.Collection;

import org.springframework.data.annotation.Id;

public class UserCollection {

	@Id
	public String id;
	
	public String userID;
	public Collection<String> objectsInCollection;

	public UserCollection() {
	}

	public UserCollection(String userID, Collection<String> objectsInCollection) {
		this.userID = userID;
		this.objectsInCollection = objectsInCollection;
	}

	@Override
	public String toString() {
		return "UserCollection [id=" + id + ", objectsInCollection=" + String.join("", objectsInCollection) + "]";
	}
}
