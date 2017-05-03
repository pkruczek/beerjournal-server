package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
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
public class Event {

    @Id
    private final ObjectId id;
    private final String action;
    private final String contentType;
    private final Object content;


    public static Event of(Action action, Object content) {
        return new Event(null, action.toString(), content.getClass().getSimpleName(), content);
    }

}
