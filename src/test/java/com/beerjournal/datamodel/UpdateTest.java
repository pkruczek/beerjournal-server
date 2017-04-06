package com.beerjournal.datamodel;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.beerjournal.datamodel.entity.BottleEntity;
import com.beerjournal.datamodel.entity.CanEntity;
import com.beerjournal.datamodel.entity.CapEntity;
import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {
	@Autowired
	public CollectableObjectsRepository collectibleObjectsRepository;
	
	@Autowired
	private UserCollectionRepository userCollectionRepository;
	
	private static final String BREWERY = "Tyskie";
	private static final String USER_ID = "testUser";
	
	@Before
	public void beforeTest() {
		DatamodelTestUtils.clearData(Lists.newArrayList(collectibleObjectsRepository, userCollectionRepository));
		persistData();
	}
	
	@After
	public void afterTest() {
		DatamodelTestUtils.clearData(Lists.newArrayList(collectibleObjectsRepository, userCollectionRepository));
	}
	
	@Test
	public void updateSingleObject() {
		String newBrewery = "Okocim";
		Collection<CollectableObjectEntity> allObjects = collectibleObjectsRepository.getAll();
		
		Assertions.assertThat(allObjects).hasSize(2);
		
		CollectableObjectEntity updatedObject = allObjects.iterator().next();
		updatedObject.setBrewery(newBrewery);
		collectibleObjectsRepository.update(updatedObject);
		
		Assertions.assertThat(collectibleObjectsRepository.getAll()).hasSize(2);
		Assertions.assertThat(collectibleObjectsRepository.getById(updatedObject.getId().get())).isNotNull();
		Assertions.assertThat(collectibleObjectsRepository.getByBrewery(newBrewery)).isNotNull();
	}
	
	@Test
	public void updateCollectionByAddingObject() {
		Collection<UserCollectionEntity> userCollections = userCollectionRepository.getUserCollections(USER_ID);
		
		Assertions.assertThat(userCollections).hasSize(1);
		
		UserCollectionEntity collection = userCollections.iterator().next();
		
		Assertions.assertThat(collection.getObjectsInCollection()).hasSize(2);
		
		CollectableObjectEntity cap = new CapEntity(USER_ID, BREWERY);
		
		collection.addCollectableObject(cap);
		
		userCollectionRepository.update(collection);
		userCollections = userCollectionRepository.getUserCollections(USER_ID);
		
		Assertions.assertThat(userCollections).hasSize(1);
		
		collection = userCollections.iterator().next();
		
		Assertions.assertThat(collection.getObjectsInCollection()).hasSize(3);
	}
	
	private void persistData() {
		CollectableObjectEntity bottle = new BottleEntity(USER_ID, BREWERY, 0.5);
		CollectableObjectEntity can = new CanEntity(USER_ID, BREWERY, 0.5);
		
		collectibleObjectsRepository.save(bottle);
		collectibleObjectsRepository.save(can);
		
		Collection<CollectableObjectEntity> collection = new ArrayList<>();
		collection.add(bottle);
		collection.add(can);
		
		UserCollectionEntity userCollection = new UserCollectionEntity(USER_ID, collection);
		
		userCollectionRepository.save(userCollection);
	}
}
