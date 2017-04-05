package com.beerjournal.datamodel.entity;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserCollectionEntity {

	private Optional<String> id;
	private String userID;
	private Collection<CollectableObjectEntity> objectsInCollection;

	/**
	 * 
	 * @param id - object identifier on data base
	 * @param userID - identifier of owner
	 * @param objectsInCollection - collection of CollectableObjects
	 */
	public UserCollectionEntity(String id, String userID, Collection<CollectableObjectEntity> objectsInCollection) {
		this.id = Optional.of(id);
		this.userID = userID;
		this.objectsInCollection = objectsInCollection;
	}
	
	/**
	 * 
	 * @param userID - identifier of owner
	 * @param objectsInCollection - collection of CollectableObjects
	 */
	public UserCollectionEntity(String userID, Collection<CollectableObjectEntity> objectsInCollection) {
		this.id = Optional.empty();
		this.userID = userID;
		this.objectsInCollection = objectsInCollection;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Collection<CollectableObjectEntity> getObjectsInCollection() {
		return objectsInCollection;
	}

	public void setObjectsInCollection(Collection<CollectableObjectEntity> objectsInCollection) {
		this.objectsInCollection = objectsInCollection;
	}
	
	/**
	 * 
	 * @return data base identifier if object is already persisted
	 */
	public Optional<String> getId() {
		return id;
	}
	
	/**
	 * DO NOT POPULATE - id is automatically set during persisting process
	 * @param id - object identifier on data base
	 */
	public void setId(String id) {
		this.id = Optional.of(id);
	}

	@Override
	public String toString() {
		String objects = String.join("", objectsInCollection.stream().map(CollectableObjectEntity::toString).collect(Collectors.toList()));
		return "UserCollection [id=" + id + ", objectsInCollection=" + objects + "]";
	}
}
