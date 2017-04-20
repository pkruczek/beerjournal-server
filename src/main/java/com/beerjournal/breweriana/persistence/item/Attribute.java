package com.beerjournal.breweriana.persistence.item;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data(staticConstructor = "of")
public class Attribute {
    private final String name;
    private final Object value;
}
