package com.beerjournal.datamodel.repository.mongo;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.model.UserCollection;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import com.beerjournal.datamodel.repository.mongo.access.UserCollectionMongoRepository;

public class UserCollectionRepositoryImpl implements UserCollectionRepository {
	
	@Autowired
	private UserCollectionMongoRepository repository;
	
	@Autowired
	private CollectableObjectsRepository collectibleObjectsRepository;
	
	@Override
	public Collection<UserCollectionEntity> getAll() {
		return repository.findAll().stream().map(this::getEntity).collect(Collectors.toList());
	}

	@Override
	public Collection<UserCollectionEntity> getAllCollections() {
		return repository.findAll().stream().map(this::getEntity).collect(Collectors.toList());
	}

	@Override
	public Collection<UserCollectionEntity> getUserCollections(String userId) {
		return repository.findByUserID(userId).stream().map(this::getEntity).collect(Collectors.toList());
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public UserCollection save(UserCollectionEntity objectToSave) {
		Collection<String> objectsInCollection = objectToSave.getObjectsInCollection()
																		.stream()
																		.map(objectInCollection -> collectibleObjectsRepository.update(objectInCollection).id)
																		.collect(Collectors.toList());
		
		UserCollection userCollection = new UserCollection(objectToSave.getUserID(), objectsInCollection);
		repository.save(userCollection);
		objectToSave.setId(userCollection.id);
		return userCollection;
	}

	@Override
	public UserCollectionEntity getById(String id) {
		return getEntity(getObjectById(id));
	}
	
	@Override
	public void delete(UserCollectionEntity objectToDelete) {
		repository.delete(objectToDelete.getId().get());
	}
	
	@Override
	public void deleteWholeCollection(UserCollectionEntity entity) {
		entity.getObjectsInCollection().stream().forEach(objectInCollection -> collectibleObjectsRepository.delete(objectInCollection));
		delete(entity);
	}

	@Override
	public UserCollection update(UserCollectionEntity objectToUpdate) {
		if (!objectToUpdate.getId().isPresent()) {
			return save(objectToUpdate);
		}
		
		objectToUpdate.getObjectsInCollection().stream()
											   .map(objectInCollection -> collectibleObjectsRepository.update(objectInCollection))
											   .collect(Collectors.toList());
		
		UserCollection userCollection = repository.findById(objectToUpdate.getId().get());
		
		userCollection.userID = objectToUpdate.getUserID();
		userCollection.objectsInCollection = objectToUpdate.getObjectsInCollection().stream()
																				    .map(CollectableObjectEntity::getId)
																				    .map(Optional::get)
																				    .collect(Collectors.toList());
		return repository.save(userCollection);
	}
	
	private UserCollection getObjectById(String id) {
		return repository.findById(id);
	}
	
	private UserCollectionEntity getEntity(UserCollection collection) {
		Collection<CollectableObjectEntity> collectibleObjects = collection.objectsInCollection
																		   .stream()
																		   .map(collectibleObjectsRepository::getById)
																		   .filter(Objects::nonNull)
																		   .collect(Collectors.toList());
		return new UserCollectionEntity(collection.id, collection.userID, collectibleObjects);
	}
}
