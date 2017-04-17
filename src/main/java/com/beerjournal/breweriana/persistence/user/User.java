package com.beerjournal.breweriana.persistence.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
public class User {

    @Id
    private final String id;
    private final String userCollectionId;
    private final String firstName;
    private final String lastName;
    private final String email;

    @PersistenceConstructor
    User(String id, String userCollectionId, String firstName, String lastName, String email) {
        this.id = id;
        this.userCollectionId = userCollectionId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Builder
    User(String userCollectionId, String firstName, String lastName, String email) {
        this(null, userCollectionId, firstName, lastName, email);
    }
}
