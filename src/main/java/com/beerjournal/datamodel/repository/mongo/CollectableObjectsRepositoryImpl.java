package com.beerjournal.datamodel.repository.mongo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.model.CollectableObject;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.mongo.access.CollectableObjectsMongoRepository;
import com.mongodb.gridfs.GridFSDBFile;

public class CollectableObjectsRepositoryImpl implements CollectableObjectsRepository {
	
	@Autowired
	private CollectableObjectsMongoRepository repository;
	
	@Autowired
	private GridFsOperations gridOperations;
	
	private static final String IMAGE_EXTENSION = "png";
	private static final String FILENAME_FIELD = "filename";
	
	@Override
	public Collection<CollectableObjectEntity> getAll() {
		return repository.findAll().stream().map(CollectableObjectsMapper::getEntity).collect(Collectors.toList());
	}
	
	@Override
	public Collection<CollectableObjectEntity> getAllObjectsForUser(String userID) {
		return repository.findByOwnerID(userID).stream().map(CollectableObjectsMapper::getEntity).collect(Collectors.toList());
	}

	@Override
	public Collection<CollectableObjectEntity> getByBrewery(String brewery) {
		return repository.findByBrewery(brewery).stream().map(CollectableObjectsMapper::getEntity).collect(Collectors.toList());
	}
	
	@Override
	public void deleteAll() {
		getAll().stream().forEach(object -> gridOperations.delete(findSingleImage(object.getId().get())));
		repository.deleteAll();
	}
	
	@Override
	public CollectableObjectEntity getById(String id) {
		return CollectableObjectsMapper.getEntity(repository.findById(id));
	}
	
	@Override
	public void save(CollectableObjectEntity objectToSave) {
		CollectableObject object = repository.save(CollectableObjectsMapper.getObject(objectToSave));
		objectToSave.setId(object.id);
	}
	
	@Override
	public void update(CollectableObjectEntity objectToUpdate) {
		if (objectToUpdate.getId().isPresent()) {
			repository.save(updateObject(objectToUpdate));
		} else {
			save(objectToUpdate);
		}
	}
	

	@Override
	public void delete(CollectableObjectEntity objectToDelete) {
		gridOperations.delete(findSingleImage(objectToDelete.getId().get()));
		repository.delete(objectToDelete.getId().get());
	}
	
	@Override
	public Optional<BufferedImage> getImageForObject(String objectID) {
		Optional<BufferedImage> result = Optional.empty();
		List<GridFSDBFile> results = gridOperations.find(findSingleImage(objectID));
		
		if (!results.isEmpty()) {
			GridFSDBFile file = results.get(0);
			
			try {
				result = Optional.ofNullable(ImageIO.read(file.getInputStream()));
			} catch (IOException e) {
				// TODO use logger
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public void saveImageForObject(CollectableObjectEntity object, InputStream image) {
		if (!object.getId().isPresent()) {
			save(object);
		}
		
		gridOperations.store(image, object.getId().get() + "." + IMAGE_EXTENSION, "image/png");
	}
	
	private Query findSingleImage(String objectID) {
		return new Query().addCriteria(Criteria.where(FILENAME_FIELD).is(objectID + "." + IMAGE_EXTENSION));
	}
	
	private CollectableObject updateObject(CollectableObjectEntity entity) {
		CollectableObject object = repository.findById(entity.getId().get());
		CollectableObjectsMapper.updateObject(entity, object);
		return object;
	}

}
