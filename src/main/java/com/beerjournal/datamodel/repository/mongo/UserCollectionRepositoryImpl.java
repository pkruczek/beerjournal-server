package com.beerjournal.datamodel.repository.mongo;

import java.awt.image.BufferedImage;
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
import com.beerjournal.datamodel.repository.mongo.access.ImageService;
import com.beerjournal.datamodel.repository.mongo.access.UserCollectionMongoRepository;

public class UserCollectionRepositoryImpl implements UserCollectionRepository {

	@Autowired
	private UserCollectionMongoRepository repository;
	
	@Autowired
	private CollectableObjectsRepository collectibleObjectsRepository;

	@Autowired
	private ImageService imageService;

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
		getAll().stream().forEach(userCollection -> imageService.deleteCollectableObjectImage(userCollection.getId().get()));
		repository.deleteAll();
	}

	@Override
	public void save(UserCollectionEntity objectToSave) {
		objectToSave.getObjectsInCollection().stream()
											 .forEach(objectInCollection -> collectibleObjectsRepository.update(objectInCollection));

		Collection<String> objectsInCollection = objectToSave.getObjectsInCollection().stream()
																					  .map(CollectableObjectEntity::getId)
																					  .map(Optional::get)
																					  .collect(Collectors.toList());
		
		UserCollection userCollection = new UserCollection(objectToSave.getName(), objectToSave.getUserID(), objectsInCollection);
		repository.save(userCollection);
		objectToSave.setId(userCollection.id);
	}

	@Override
	public UserCollectionEntity getById(String id) {
		return getEntity(getObjectById(id));
	}

	@Override
	public void delete(UserCollectionEntity objectToDelete) {
		imageService.deleteUserCollectionImage(objectToDelete.getId().get());
		repository.delete(objectToDelete.getId().get());
	}

	@Override
	public void deleteWholeCollection(UserCollectionEntity entity) {
		entity.getObjectsInCollection().stream().forEach(objectInCollection -> collectibleObjectsRepository.delete(objectInCollection));
		imageService.deleteUserCollectionImage(entity.getId().get());
		delete(entity);
	}

	@Override
	public void update(UserCollectionEntity objectToUpdate) {
		if (!objectToUpdate.getId().isPresent()) {
			save(objectToUpdate);
			return;
		}

		objectToUpdate.getObjectsInCollection().stream().forEach(objectInCollection -> collectibleObjectsRepository.update(objectInCollection));

		UserCollection userCollection = repository.findById(objectToUpdate.getId().get());

		userCollection.name = objectToUpdate.getName();
		userCollection.userID = objectToUpdate.getUserID();
		userCollection.objectsInCollection = objectToUpdate.getObjectsInCollection().stream()
																				    .map(CollectableObjectEntity::getId)
																				    .map(Optional::get)
																				    .collect(Collectors.toList());
		repository.save(userCollection);
	}

	@Override
	public Optional<BufferedImage> getImageForUserCollection(String userCollectionId) {
		return imageService.getImageForUserCollection(userCollectionId);
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
		return new UserCollectionEntity(collection.name, collection.id, collection.userID, collectibleObjects);
	}
}
