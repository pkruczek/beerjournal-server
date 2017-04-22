package com.beerjournal.infrastructure.mapping;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Module objectIdMapper() {
        return new SimpleModule()
                .addSerializer(new ObjectIdSerializer(ObjectId.class))
                .addDeserializer(ObjectId.class, new ObjectIdDeserializer(ObjectId.class));
    }
}
