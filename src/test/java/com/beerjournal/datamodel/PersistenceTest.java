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
public class PersistenceTest {
	
	@Autowired
	public CollectableObjectsRepository collectibleObjectsRepository;
	
	@Autowired
	private UserCollectionRepository userCollectionRepository;
	
	private static final String BREWERY = "Tyskie";
	private static final String USER_ID = "testUser";
	
	@Before
	public void beforeTest() {
		DatamodelTestUtils.clearData(Lists.newArrayList(collectibleObjectsRepository, userCollectionRepository));
	}
	
	@After
	public void afterTest() {
		DatamodelTestUtils.clearData(Lists.newArrayList(collectibleObjectsRepository, userCollectionRepository));
	}
	
	@Test
	public void peristSingleObjects() {		
		String brewery = "Tyskie";
		
		collectibleObjectsRepository.save(new BottleEntity(USER_ID, brewery, 0.5));
		collectibleObjectsRepository.save(new CanEntity(USER_ID, brewery, 0.5));
		
		Collection<CollectableObjectEntity> objectsFromBrewery = collectibleObjectsRepository.getByBrewery(brewery);
		
		System.out.println("Object from " + brewery);
		objectsFromBrewery.forEach(System.out::println);
		System.out.println("---------------------------");
		
		Assertions.assertThat(objectsFromBrewery).hasSize(2);
	}
	
	@Test
	public void peristUserCollection() {				
		CollectableObjectEntity bottle = new BottleEntity(USER_ID, BREWERY, 0.5);
		CollectableObjectEntity can = new CanEntity(USER_ID, BREWERY, 0.5);
		
		collectibleObjectsRepository.save(bottle);
		collectibleObjectsRepository.save(can);
		
		Collection<CollectableObjectEntity> collection = new ArrayList<>();
		collection.add(bottle);
		collection.add(can);
		
		UserCollectionEntity userCollection = new UserCollectionEntity(USER_ID, collection);
		
		userCollectionRepository.save(userCollection);
		
		Collection<CollectableObjectEntity> objectsFromBrewery = collectibleObjectsRepository.getByBrewery(BREWERY);
		Collection<UserCollectionEntity> collectionForUser = userCollectionRepository.getUserCollections(USER_ID);
		
		System.out.println("Object from " + BREWERY);
		objectsFromBrewery.forEach(System.out::println);
		System.out.println("---------------------------");
		System.out.println("Collection owned by " + USER_ID);
		collectionForUser.forEach(System.out::println);
		System.out.println("---------------------------");
		
		Assertions.assertThat(objectsFromBrewery).hasSize(2);
		Assertions.assertThat(collectionForUser).hasSize(1);
	}
}
