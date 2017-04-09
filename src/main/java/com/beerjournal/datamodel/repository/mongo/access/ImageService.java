package com.beerjournal.datamodel.repository.mongo.access;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.mongodb.gridfs.GridFSDBFile;

@Component
public class ImageService {
	@Autowired
	private GridFsOperations gridOperations;
	
	private static final String IMAGE_EXTENSION = "png";
	private static final String FILENAME_FIELD = "filename";
	private static final String COLLECTABLE_OBJECT_SUFIX = "CollectableObject";
	private static final String USER_COLLECTION_SUFIX = "UserCollection";
	
	public Optional<BufferedImage> getImageForCollectableObject(String objectID) {
		return getImage(objectID, COLLECTABLE_OBJECT_SUFIX);
	}
	
	public Optional<BufferedImage> getImageForUserCollection(String collectionID) {
		return getImage(collectionID, USER_COLLECTION_SUFIX);
	}
	
	public void saveImageForCollectableObject(CollectableObjectEntity object, InputStream image) {
		storeImage(image, object.getId().get(), COLLECTABLE_OBJECT_SUFIX);
	}
	
	public void saveImageForuserCollection(UserCollectionEntity userCollection, InputStream image) {	
		storeImage(image, userCollection.getId().get(), USER_COLLECTION_SUFIX);
	}
	
	public void deleteCollectableObjectImage(String objectID) {
		deleteImage(objectID, COLLECTABLE_OBJECT_SUFIX);
	}
	
	public void deleteUserCollectionImage(String collectionID) {
		deleteImage(collectionID, USER_COLLECTION_SUFIX);
	}
	
	private void storeImage(InputStream image, String objectID, String sufix) {
		gridOperations.store(image, getFileName(objectID, sufix), "image/png");
	}
	
	private void deleteImage(String objectID, String sufix) {
		gridOperations.delete(findSingleImage(objectID, sufix));
	}
	
	private Optional<BufferedImage> getImage(String objectID, String sufix) {
		Optional<BufferedImage> result = Optional.empty();
		List<GridFSDBFile> results = gridOperations.find(findSingleImage(objectID, sufix));
		
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
	
	private String getFileName(String objectID, String sufix) {
		return objectID + sufix + "." + IMAGE_EXTENSION;
	}
	
	private Query findSingleImage(String objectID, String sufix) {
		return new Query().addCriteria(Criteria.where(FILENAME_FIELD).is(getFileName(objectID, sufix)));
	}
}
