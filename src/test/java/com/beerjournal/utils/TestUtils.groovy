package com.beerjournal.utils

import com.beerjournal.breweriana.persistence.user.User
import lombok.AccessLevel
import lombok.NoArgsConstructor

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class TestUtils {

    static def someUser() {
        User.builder()
                .firstName("Janusz")
                .lastName("Nowak")
                .email("nowak@piwo.pl")
                .build()
    }

}
