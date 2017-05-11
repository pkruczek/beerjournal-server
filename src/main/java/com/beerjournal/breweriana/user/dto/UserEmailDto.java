package com.beerjournal.breweriana.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class UserEmailDto {
    @NotEmpty private final String password;
    @NotEmpty private final String email;
    @NotEmpty private final String newEmail;
}
