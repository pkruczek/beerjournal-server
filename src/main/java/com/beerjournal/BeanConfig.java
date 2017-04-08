package com.beerjournal;

import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import com.beerjournal.datamodel.repository.UserRepository;
import com.beerjournal.datamodel.repository.mongo.CollectableObjectsRepositoryImpl;
import com.beerjournal.datamodel.repository.mongo.UserCollectionRepositoryImpl;
import com.beerjournal.datamodel.repository.mongo.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }

}
