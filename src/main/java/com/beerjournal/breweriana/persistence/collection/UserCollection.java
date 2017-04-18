package com.beerjournal.breweriana.persistence.collection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Set;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
public class UserCollection {

    @Id
    private final ObjectId id;
    private final ObjectId ownerId;
    private final Set<ItemRef> itemRefs;

    @PersistenceConstructor
    UserCollection(ObjectId id, ObjectId ownerId, Set<ItemRef> itemRefs) {
        this.id = id;
        this.ownerId = ownerId;
        this.itemRefs = itemRefs;
    }

    @Builder
    UserCollection(ObjectId ownerId, Set<ItemRef> itemRefs) {
        this(null, ownerId, itemRefs == null ? Collections.emptySet() : itemRefs);
    }
}
