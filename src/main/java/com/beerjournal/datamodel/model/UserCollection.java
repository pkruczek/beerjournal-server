package com.beerjournal.datamodel.model;

import java.util.Collection;

import org.springframework.data.annotation.Id;

public class UserCollection {

	@Id
	public String id;
	
	public String userID;
	public String name;
	public Collection<String> objectsInCollection;

	public UserCollection() {
	}

	public UserCollection(String name, String userID, Collection<String> objectsInCollection) {
		this.name = name;
		this.userID = userID;
		this.objectsInCollection = objectsInCollection;
	}

	@Override
	public String toString() {
		return "UserCollection [id=" + id + ", userID=" + userID + ", name=" + name + ", objectsInCollection="
				+ objectsInCollection + "]";
	}
}
