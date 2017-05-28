package com.beerjournal.breweriana.item.rating.persistence;

import lombok.Builder;
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
public final class Rating {
    @Id
    private final ObjectId id;
    private final ObjectId itemId;
    private final ObjectId raterId;
    private final int value;

    @Builder
    public static Rating of(ObjectId itemId, ObjectId raterId, int value) {
        return new Rating(null, itemId, raterId, value);
    }
}
