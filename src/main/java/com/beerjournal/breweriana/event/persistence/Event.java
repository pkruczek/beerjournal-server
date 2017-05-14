package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
import com.beerjournal.breweriana.event.DataType;
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
public final class Event {

    @Id
    private final ObjectId id;
    private final String action;
    private final String dataType;
    private final Object data;

    public static Event of(Action action, Object data, DataType dataType) {
        return new Event(null, action.toString(), dataType.toString(), data);
    }

}
