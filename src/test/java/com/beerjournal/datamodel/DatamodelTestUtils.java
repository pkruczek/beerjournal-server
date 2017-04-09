package com.beerjournal.datamodel;

import java.util.ArrayList;
import java.util.Collection;

import com.beerjournal.datamodel.entity.BottleEntity;
import com.beerjournal.datamodel.entity.CanEntity;
import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.Repository;
import com.beerjournal.datamodel.repository.UserCollectionRepository;

public abstract class DatamodelTestUtils {
	public static void clearData(Collection<Repository<?>> repositories) {
		repositories.forEach(Repository::deleteAll);
	}
	
	public static void persistDataForUpdateAndDeleteTest(String userID, String brewery, 
													     CollectableObjectsRepository collectibleObjectsRepository, 
													     UserCollectionRepository userCollectionRepository) {
		
		CollectableObjectEntity bottle = new BottleEntity(userID, brewery, 0.5);
		CollectableObjectEntity can = new CanEntity(userID, brewery, 0.5);
		
		collectibleObjectsRepository.save(bottle);
		collectibleObjectsRepository.save(can);
		
		Collection<CollectableObjectEntity> collection = new ArrayList<>();
		collection.add(bottle);
		collection.add(can);
		
		UserCollectionEntity userCollection = new UserCollectionEntity("MyCollection", userID, collection);
		
		userCollectionRepository.save(userCollection);
	}
}
