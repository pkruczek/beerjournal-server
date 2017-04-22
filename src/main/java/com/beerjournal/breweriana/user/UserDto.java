package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.persistence.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class UserDto {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;

    static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId().toHexString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    static User fromDto(UserDto userDto){
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .build();
    }

}
