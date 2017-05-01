package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.utils.ServiceUtils;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class UserDto {

    private final String id;

    @NotEmpty private final String firstName;
    @NotEmpty private final String lastName;
    @NotEmpty private final String password;
    @Email private final String email;

    static UserDto of(User user){
        return UserDto.builder()
                .id(user.getId().toHexString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    static User asUser(UserDto userDto){
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .build();
    }

    static User asModifiableUser(String id, UserDto userDto){
        return User.ofModifiable(ServiceUtils.stringToObjectId(id), asUser(userDto));
    }

}
