package com.beerjournal.breweriana.item.persistence;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
@RequiredArgsConstructor(access = PRIVATE)
public class Item {

    @Id
    private final ObjectId id;
    private final ObjectId ownerId;
    private final String name;
    private final String type;
    private final String country;
    private final String brewery;
    private final String style;
    private final Set<Attribute> attributes;

    @Builder
    public static Item of(ObjectId ownerId, String name, String type, String country, String brewery, String style,
                   Set<Attribute> attributes) {
        return new Item(null, ownerId, name, type, country, brewery, style, attributes);
    }

    public ItemRef asItemRef() {
        return ItemRef.builder()
                .name(name)
                .type(type)
                .itemId(id)
                .build();
    }

    public static Item copyWithAssignedId(ObjectId id, Item item) {
        return new Item(id, item.ownerId, item.name, item.type, item.country, item.brewery, item.style, item.attributes);
    }
}
