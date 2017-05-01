package com.beerjournal.breweriana.collection.persistence;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class ItemRef {
    private final ObjectId itemId;
    private final String name;
    private final String type;
    //TODO: picture
}
