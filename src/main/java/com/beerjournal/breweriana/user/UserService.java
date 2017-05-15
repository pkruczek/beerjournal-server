package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_DUPLICATE_EMAIL;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    Page<UserDto> findUsers(int page, int count, String firstName) {
        return userRepository
                .findByFirstNameStartsWith(page, count, firstName)
                .map(UserDto::of);
    }

    UserDto createUser(UserDto userDto) {
        try {
            User savedUser = userRepository.save(toUser(userDto));
            return UserDto.of(savedUser);
        } catch (DuplicateKeyException ex) {
            throw new BeerJournalException(USER_DUPLICATE_EMAIL);
        }
    }

    UserDto getUserWithId(String userId) {
        User user = userRepository.findOneById(Converters.toObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));
        return UserDto.of(user);
    }

    private User toUser(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(encodePassword(userDto.getPassword()))
                .build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}

