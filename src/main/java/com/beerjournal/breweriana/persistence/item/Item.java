package com.beerjournal.breweriana.persistence.item;

import com.beerjournal.breweriana.persistence.collection.ItemRef;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
public class Item {

    @Id
    private final ObjectId id;
    private final ObjectId ownerId;
    private final String name;
    private final String category;
    private final String country;
    private final String brewery;
    private final String style;
    private final Set<Attribute> attributes;

    @PersistenceConstructor
    Item(ObjectId id, ObjectId ownerId, String name, String category, String country, String brewery,
         String style, Set<Attribute> attributes) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.category = category;
        this.country = country;
        this.brewery = brewery;
        this.style = style;
        this.attributes = attributes;
    }

    @Builder
    Item(ObjectId ownerId, String name, String category, String country, String brewery, String style,
         Set<Attribute> attributes) {
        this(null, ownerId, name, category, country, brewery, style, attributes);
    }

    public ItemRef asItemRef() {
        return ItemRef.builder()
                .name(name)
                .category(category)
                .itemId(id)
                .build();
    }
}
