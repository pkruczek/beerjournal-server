package com.beerjournal.breweriana.persistence.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
@RequiredArgsConstructor(access = PRIVATE)
public class User {

    @Id
    private final ObjectId id;
    private final String firstName;
    private final String lastName;
    @Indexed(unique = true)
    private final String email;

    @Builder
    public static User of(String firstName, String lastName, String email) {
        return new User(null, firstName, lastName, email);
    }

}
