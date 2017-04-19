package com.beerjournal.breweriana.persistence.collection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
@RequiredArgsConstructor(access = PRIVATE)
public class UserCollection {

    @Id
    private final ObjectId id;
    private final ObjectId ownerId;
    private final Set<ItemRef> itemRefs;

    @Builder
    public static UserCollection of(ObjectId ownerId, Set<ItemRef> itemRefs) {
        return new UserCollection(null, ownerId, itemRefs == null ? Collections.emptySet() : itemRefs);
    }

}
