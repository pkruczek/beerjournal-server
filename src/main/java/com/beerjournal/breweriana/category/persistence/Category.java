package com.beerjournal.breweriana.category.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor(access = PRIVATE)
public class Category {

    @Id
    private final ObjectId id;
    @Indexed(unique = true)
    private final String name;
    private final Set<String> values;

    public static Category of(String name, Set<String> values) {
        return new Category(null, name, values);
    }

}
