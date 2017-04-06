package com.beerjournal.datamodel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

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
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImagePersistTest {

	@Autowired
	private CollectableObjectsRepository collectibleObjectsRepository;
	
	
	private static final String BREWERY = "Tyskie";
	private static final String USER_ID = "testUser";
	
	@Before
	public void beforeTest() {
		DatamodelTestUtils.clearData(Lists.newArrayList(collectibleObjectsRepository));
		persistData();
	}
	
	@After
	public void afterTest() {
		DatamodelTestUtils.clearData(Lists.newArrayList(collectibleObjectsRepository));
	}
	
	@Test
	public void getImage() throws IOException {
		CollectableObjectEntity entity = collectibleObjectsRepository.getByBrewery(BREWERY).iterator().next();
		collectibleObjectsRepository.saveImageForObject(entity, getImageFromResources());
		
		Optional<BufferedImage> image = collectibleObjectsRepository.getImageForObject(entity.getId().get());
		
		Assertions.assertThat(image.get()).isNotNull();
		
		try {
		    File outputfile = new File("saved.png");
		    ImageIO.write(image.get(), "png", outputfile);
		} catch (IOException e) {
		   	// TODO use logger
		}
	}
	
	private InputStream getImageFromResources() {
		return getClass().getClassLoader().getResourceAsStream("tyskie.png");
	}
	
	private void persistData() {
		CollectableObjectEntity bottle = new BottleEntity(USER_ID, BREWERY, 0.5);
		CollectableObjectEntity can = new CanEntity(USER_ID, BREWERY, 0.5);
		
		collectibleObjectsRepository.save(bottle);
		collectibleObjectsRepository.save(can);		
	}
}
