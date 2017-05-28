package com.beerjournal.breweriana.item.persistence;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Wither;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@Wither
@EqualsAndHashCode(exclude = {"id"})
@RequiredArgsConstructor(access = PRIVATE)
public final class Item {

    @Id
    private final ObjectId id;
    private final ObjectId ownerId;
    private final String name;
    private final String type;
    private final String country;
    private final String brewery;
    private final String style;
    private final Set<Attribute> attributes;
    private final Set<ObjectId> imageIds;
    private final double averageRating;

    @Builder
    static Item of(ObjectId ownerId, String name, String type, String country, String brewery, String style,
                   @Singular Set<Attribute> attributes, @Singular Set<ObjectId> imageIds, double averageRating) {
        return new Item(null, ownerId, name, type, country, brewery, style, attributes, imageIds, averageRating);
    }

    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    public Set<ObjectId> getImageIds() {
        return Collections.unmodifiableSet(imageIds);
    }

    public ItemRef asItemRef() {
        return ItemRef.builder()
                .name(name)
                .type(type)
                .itemId(id)
                .build();
    }

    public Item withNewImageId(ObjectId imageId) {
        return withImageIds(Sets.union(this.imageIds, ImmutableSet.of(imageId)));
    }

    public Item withoutImageId(ObjectId imageId) {
        return withImageIds(Sets.difference(this.imageIds, ImmutableSet.of(imageId)));
    }

}
