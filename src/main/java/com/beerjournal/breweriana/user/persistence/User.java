package com.beerjournal.breweriana.user.persistence;

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
    private final String password;

    @Builder
    public static User of(String firstName, String lastName, String email, String password) {
        return new User(null, firstName, lastName, email, password);
    }

    public static User copyWithAssignedId(ObjectId id, User user) {
        return new User(id, user.firstName, user.lastName, user.email, user.password);
    }

}
