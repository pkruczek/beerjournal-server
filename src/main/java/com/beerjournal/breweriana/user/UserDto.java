package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.user.persistence.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

import static com.beerjournal.breweriana.utils.Converters.toStringId;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class UserDto {

    private final String id;

    @NotEmpty private final String firstName;
    @NotEmpty private final String lastName;
    @NotEmpty private final String password;
    @Email @NotEmpty private final String email;
    private final String avatarFileId;
    private final LocalDateTime created;

    public static UserDto of(User user){
        return UserDto.builder()
                .id(toStringId(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarFileId(toStringId(user.getAvatarFileId()))
                .created(user.getCreated())
                .build();
    }

}
