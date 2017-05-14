package com.beerjournal.breweriana.item.persistence;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data(staticConstructor = "of")
public final class Attribute {
    private final String name;
    private final String value;
}
