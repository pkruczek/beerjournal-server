package com.beerjournal.breweriana.user.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@Wither
@EqualsAndHashCode(exclude = {"id", "created"})
@RequiredArgsConstructor(access = PRIVATE)
public final class User {

    @Id
    private final ObjectId id;
    private final String firstName;
    private final String lastName;
    @Indexed(unique = true)
    private final String email;
    private final String password;
    private final ObjectId avatarFileId;
    private final LocalDateTime created;

    @Builder
    static User of(String firstName, String lastName, String email, String password, ObjectId avatarFileId) {
        return new User(null, firstName, lastName, email, password, avatarFileId, null);
    }

}
