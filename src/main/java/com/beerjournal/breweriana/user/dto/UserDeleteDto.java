package com.beerjournal.breweriana.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class UserDeleteDto {
    @NotEmpty private final String email;
    @NotEmpty private final String password;
}
