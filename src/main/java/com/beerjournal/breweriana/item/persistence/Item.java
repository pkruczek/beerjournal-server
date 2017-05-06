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
    private final Set<String> images;

    @Builder
    public static Item of(ObjectId ownerId, String name, String type, String country, String brewery, String style,
                   Set<Attribute> attributes, Set<String> images) {
        return new Item(null, ownerId, name, type, country, brewery, style, attributes, images);
    }

    public ItemRef asItemRef() {
        return ItemRef.builder()
                .name(name)
                .type(type)
                .itemId(id)
                .build();
    }

    public static Item copyWithAssignedId(ObjectId id, Item item) {
        return new Item(id, item.ownerId, item.name, item.type, item.country, item.brewery, item.style, item.attributes, item.images);
    }

    public static Item copyWithOrWithoutImage(Item item, String imageId, boolean isAdd) {
        Set<String> images = item.images;
        if (isAdd) images.add(imageId);
        else images.remove(imageId);
        return new Item(item.id, item.ownerId, item.name, item.type, item.country, item.brewery, item.style, item.attributes, images);
    }
}
