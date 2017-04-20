package com.beerjournal.breweriana.persistence.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor(access = PRIVATE)
public class Category {

    @Id
    private final ObjectId id;
    private final String name;

    public static Category of(String name) {
        return new Category(null, name);
    }

}
