package com.beerjournal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import com.beerjournal.datamodel.repository.mongo.CollectableObjectsRepositoryImpl;
import com.beerjournal.datamodel.repository.mongo.UserCollectionRepositoryImpl;

@Configuration
public class BeanConfig {
	@Bean
	public CollectableObjectsRepository getCollectibleObjectsRepository() {
		return new CollectableObjectsRepositoryImpl();
	}
	
	@Bean
	public UserCollectionRepository getUserCollectionRepository() {
		return new UserCollectionRepositoryImpl();
	}
}
