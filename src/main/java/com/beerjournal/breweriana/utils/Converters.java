package com.beerjournal.breweriana.utils;

import com.beerjournal.infrastructure.error.BeerJournalException;
import com.google.common.collect.ImmutableMap;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

import static com.beerjournal.infrastructure.error.ErrorInfo.MALFORMED_ID;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Converters {

    public static ObjectId toObjectId(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new BeerJournalException(MALFORMED_ID);
        }
        return objectId;
    }

    public static Stream<ObjectId> toObjectIds(Collection<String> objectIds) {
        return objectIds.stream()
                .map(Converters::toObjectId);
    }

    public static String toStringId(ObjectId objectId) {
        return objectId == null ? null : objectId.toString();
    }

    public static Stream<String> toStringIds(Collection<ObjectId> objectIds) {
        return objectIds.stream()
                .map(Converters::toStringId);
    }

    public static Instant toInstant(ObjectId objectId) {
        return new Date(objectId.getTimestamp() * 1000L).toInstant();
    }

    public static Map<String, String> toMap(ObjectId id) {
        if (id == null) {
            return null;
        }
        return ImmutableMap.of("id", toStringId(id));
    }

}
