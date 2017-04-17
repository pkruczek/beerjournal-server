package com.beerjournal.breweriana.persistence.collection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
public class UserCollection {

    @Id
    private final String id;
    private final String userId;
    private final Set<Item> items;

    @PersistenceConstructor
    UserCollection(String id, String userId, Set<Item> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    @Builder
    UserCollection(String userId, Set<Item> items) {
        this(null, userId, items);
    }
}
