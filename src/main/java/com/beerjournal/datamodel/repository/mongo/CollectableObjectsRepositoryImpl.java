package com.beerjournal.datamodel.repository.mongo;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.model.CollectableObject;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.mongo.access.CollectableObjectsMongoRepository;

public class CollectableObjectsRepositoryImpl implements CollectableObjectsRepository {
	
	@Autowired
	private CollectableObjectsMongoRepository repository;
	
	@Override
	public Collection<CollectableObjectEntity> getAll() {
		return repository.findAll().stream().map(CollectableObjectsMapper::getEntity).collect(Collectors.toList());
	}

	@Override
	public Collection<CollectableObjectEntity> getByBrewery(String brewery) {
		return repository.findByBrewery(brewery).stream().map(CollectableObjectsMapper::getEntity).collect(Collectors.toList());
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
	
	@Override
	public CollectableObjectEntity getById(String id) {
		return CollectableObjectsMapper.getEntity(getObjectById(id));
	}
	
	@Override
	public CollectableObject getObjectById(String id) {
		return repository.findById(id);
	}
	
	@Override
	public CollectableObject save(CollectableObjectEntity objectToSave) {
		CollectableObject object = repository.save(CollectableObjectsMapper.getObject(objectToSave));
		objectToSave.setId(object.id);
		return object;
	}
	
	@Override
	public CollectableObject update(CollectableObjectEntity objectToUpdate) {
		if (objectToUpdate.getId().isPresent()) {
			return repository.save(updateObject(objectToUpdate));
		} 
		return save(objectToUpdate);
	}
	

	@Override
	public void delete(CollectableObjectEntity objectToDelete) {
		repository.delete(objectToDelete.getId().get());
	}
	
	//TODO
	private CollectableObject updateObject(CollectableObjectEntity entity) {
		CollectableObject object = getObjectById(entity.getId().get());
		
		object.brewery = entity.getBrewery();
		object.ownerID = entity.getOwnerID();

		return object;
	}

}
