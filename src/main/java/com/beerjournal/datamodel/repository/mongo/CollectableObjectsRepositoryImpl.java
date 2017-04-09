package com.beerjournal.datamodel.repository.mongo;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.model.CollectableObject;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.mongo.access.CollectableObjectsMongoRepository;
import com.beerjournal.datamodel.repository.mongo.access.ImageService;

public class CollectableObjectsRepositoryImpl implements CollectableObjectsRepository {
	
	@Autowired
	private CollectableObjectsMongoRepository repository;
	
	@Autowired
	private ImageService imageService;
	
	@Override
	public Collection<CollectableObjectEntity> getAll() {
		return repository.findAll().stream().map(CollectableObject::getEntity).collect(Collectors.toList());
	}
	
	@Override
	public Collection<CollectableObjectEntity> getAllObjectsForUser(String userID) {
		return repository.findByOwnerID(userID).stream().map(CollectableObject::getEntity).collect(Collectors.toList());
	}

	@Override
	public Collection<CollectableObjectEntity> getByBrewery(String brewery) {
		return repository.findByBrewery(brewery).stream().map(CollectableObject::getEntity).collect(Collectors.toList());
	}
	
	@Override
	public void deleteAll() {
		getAll().stream().forEach(object -> imageService.deleteCollectableObjectImage(object.getId().get()));
		repository.deleteAll();
	}
	
	@Override
	public CollectableObjectEntity getById(String id) {
		CollectableObject collectableObject = repository.findById(id);
		if (Objects.isNull(collectableObject)) {
			return null;
		}
		return collectableObject.getEntity();
	}
	
	@Override
	public void save(CollectableObjectEntity objectToSave) {
		CollectableObject object = repository.save(objectToSave.getObject());
		objectToSave.setId(object.id);
	}
	
	@Override
	public void update(CollectableObjectEntity objectToUpdate) {
		if (objectToUpdate.getId().isPresent()) {
			CollectableObject object = repository.findById(objectToUpdate.getId().get());
			repository.save(object.update(objectToUpdate));
		} else {
			save(objectToUpdate);
		}
	}
	

	@Override
	public void delete(CollectableObjectEntity objectToDelete) {
		imageService.deleteCollectableObjectImage(objectToDelete.getId().get());
		repository.delete(objectToDelete.getId().get());
	}
	
	@Override
	public Optional<BufferedImage> getImageForObject(String objectID) {
		return imageService.getImageForCollectableObject(objectID);
	}
	
	@Override
	public void saveImageForObject(CollectableObjectEntity object, InputStream image) {
		if (!object.getId().isPresent()) {
			save(object);
		}
		
		imageService.saveImageForCollectableObject(object, image);
	}
}
