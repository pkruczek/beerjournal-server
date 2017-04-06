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
import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {
	@Autowired
	private CollectableObjectsRepository collectibleObjectsRepository;
	
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
	public void removeSingleObject() {
		Collection<UserCollectionEntity> userCollection = userCollectionRepository.getUserCollections(USER_ID);
		Collection<CollectableObjectEntity> collectableObjects = collectibleObjectsRepository.getByBrewery(BREWERY);
		
		Assertions.assertThat(userCollection).hasSize(1);
		Assertions.assertThat(collectableObjects).hasSize(2);
		
		collectibleObjectsRepository.delete(collectableObjects.iterator().next());
		
		Assertions.assertThat(userCollectionRepository.getUserCollections(USER_ID)).hasSize(1);
		Assertions.assertThat(userCollectionRepository.getUserCollections(USER_ID).iterator().next().getObjectsInCollection()).hasSize(1);
		Assertions.assertThat(collectibleObjectsRepository.getByBrewery(BREWERY)).isNotEmpty();
	}
	
	@Test
	public void removeCollectionObject() {
		Collection<UserCollectionEntity> userCollection = userCollectionRepository.getUserCollections(USER_ID);
		
		Assertions.assertThat(userCollection).hasSize(1);
		
		userCollectionRepository.delete(userCollection.iterator().next());
		
		Assertions.assertThat(userCollectionRepository.getUserCollections(USER_ID)).hasSize(0);
		Assertions.assertThat(collectibleObjectsRepository.getByBrewery(BREWERY)).isNotEmpty();
	}
	
	@Test
	public void removeWholeCollection() {
		Collection<UserCollectionEntity> userCollection = userCollectionRepository.getUserCollections(USER_ID);
		
		Assertions.assertThat(userCollection).hasSize(1);
		
		userCollectionRepository.deleteWholeCollection(userCollection.iterator().next());
		
		Assertions.assertThat(userCollectionRepository.getUserCollections(USER_ID)).hasSize(0);
		Assertions.assertThat(collectibleObjectsRepository.getByBrewery(BREWERY)).isEmpty();
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
