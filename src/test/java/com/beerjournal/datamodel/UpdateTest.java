package com.beerjournal.datamodel;

import java.util.Collection;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.beerjournal.datamodel.entity.BottleEntity;
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
		DatamodelTestUtils.persistDataForUpdateAndDeleteTest(USER_ID, BREWERY, collectibleObjectsRepository, userCollectionRepository);
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
	
	@Test
	public void updateVolume() {
		double newVolume = 3;
		
		Collection<CollectableObjectEntity> allObjects = collectibleObjectsRepository.getAll();
		BottleEntity bottle = findBottleEntity(allObjects).get();
		
		bottle.setVolume(newVolume);
		
		collectibleObjectsRepository.update(bottle);
		bottle = (BottleEntity)collectibleObjectsRepository.getById(bottle.getId().get());
		
		Assertions.assertThat(bottle.getVolume()).isEqualTo(newVolume);
	}
	
	private Optional<BottleEntity> findBottleEntity(Collection<CollectableObjectEntity> entities) {
		return entities.stream().filter(entity -> entity instanceof BottleEntity).map(entity -> (BottleEntity)entity).findAny();
	}
}
