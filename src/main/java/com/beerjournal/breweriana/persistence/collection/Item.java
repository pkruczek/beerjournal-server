package com.beerjournal.breweriana.persistence.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Item {
    private final String itemDetailsId;
    private final String name;
    private final String category;
    //TODO: picture
}
