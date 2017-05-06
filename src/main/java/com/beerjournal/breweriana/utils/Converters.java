package com.beerjournal.breweriana.utils;

import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Date;

import static com.beerjournal.infrastructure.error.ErrorInfo.INCORRECT_USER_ID;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Converters {

    public static ObjectId toObjectId(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new BeerJournalException(INCORRECT_USER_ID);
        }
        return objectId;
    }

    public static Instant toInstant(ObjectId objectId){
        return new Date(objectId.getTimestamp() * 1000L).toInstant();
    }

}
