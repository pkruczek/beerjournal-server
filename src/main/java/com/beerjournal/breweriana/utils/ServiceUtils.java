package com.beerjournal.breweriana.utils;

import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import static com.beerjournal.infrastructure.error.ErrorInfo.INCORRECT_USER_ID;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ServiceUtils {

    public static ObjectId stringToObjectId(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new BeerJournalException(INCORRECT_USER_ID);
        }
        return objectId;
    }

}
