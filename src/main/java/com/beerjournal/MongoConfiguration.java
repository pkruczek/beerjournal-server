package com.beerjournal;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;


@Configuration
@EnableMongoRepositories({"com.beerjournal"})
public class MongoConfiguration extends AbstractMongoConfiguration{

    @Value("${mongodb.databaseName}")
    private String databaseName;

    @Value("${mongodb.databaseHost}")
    private String databaseHost;

    @Value("${mongodb.databasePort}")
    public int databasePort;

    @Value("${mongodb.userName}")
    public String userName;

    @Value("${mongodb.password}")
    public String password;

    @Value("${mongodb.authenticatedDatabaseName}")
    public String authenticatedDatabaseName;

    @Value("${mongodb.isDatabaseAuthenticated}")
    public boolean isDatabaseAuthenticated;


    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        ServerAddress server = new ServerAddress(databaseHost, databasePort);
        if(isDatabaseAuthenticated) {
            MongoCredential credentials = MongoCredential.createCredential(userName, authenticatedDatabaseName, password.toCharArray());
            return new MongoClient(server, Collections.singletonList(credentials));
        }
        return new MongoClient(server);
    }

    @Bean
    public MongoDatabase database() {
        try {
            MongoClient mongoClient = (MongoClient) mongo();
            return mongoClient.getDatabase(databaseName);
        } catch (Exception e) {
            throw new RuntimeException("[MONGO] could not connect to database! " + e);
        }
    }
}
