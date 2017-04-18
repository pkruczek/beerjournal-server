package com.beerjournal.breweriana.persistence.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
public class User {

    @Id
    private final ObjectId id;
    private final String firstName;
    private final String lastName;
    private final String email;

    @PersistenceConstructor
    User(ObjectId id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Builder
    User(String firstName, String lastName, String email) {
        this(null, firstName, lastName, email);
    }
}
