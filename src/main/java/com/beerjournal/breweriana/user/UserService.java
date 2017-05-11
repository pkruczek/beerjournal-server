package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.user.dto.UserDeleteDto;
import com.beerjournal.breweriana.user.dto.UserDetailsDto;
import com.beerjournal.breweriana.user.dto.UserDto;
import com.beerjournal.breweriana.user.dto.UserEmailDto;
import com.beerjournal.breweriana.user.dto.UserPasswordDto;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_DUPLICATE_EMAIL;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_FORBIDDEN_MODIFICATION;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_INCORRECT_EMAIL;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_INCORRECT_PASSWORD;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    Set<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::of)
                .collect(Collectors.toSet());
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

    UserDto modifyUserWithId(String userId, UserDetailsDto user) {
        User currentUser = getUserForModification(userId, user.getPassword());
        User modifiedUser = currentUser
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName());
        User updatedUser = userRepository.update(modifiedUser);
        return UserDto.of(updatedUser);
    }

    UserDto deleteUserWithId(String userId, UserDeleteDto user) {
        User currentUser = getUserForModification(userId, user.getPassword());
        verifyEmailEquality(user.getEmail(), currentUser);
        User deletedUser = userRepository.deleteOneById(currentUser.getId());
        return UserDto.of(deletedUser);
    }

    void modifyUserPassword(String userId, UserPasswordDto user) {
        User currentUser = getUserForModification(userId, user.getPassword());
        User modifiedUser = currentUser.withPassword(encodePassword(user.getNewPassword()));
        userRepository.update(modifiedUser);
    }

    void modifyUserEmail(String userId, UserEmailDto user) {
        User currentUser = getUserForModification(userId, user.getPassword());
        verifyEmailEquality(user.getEmail(), currentUser);
        User modifiedUser = currentUser.withEmail(user.getNewEmail());
        userRepository.update(modifiedUser);
    }

    private User toUser(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(encodePassword(userDto.getPassword()))
                .build();
    }

    private User getUserForModification(String userId, String password) {
        ObjectId userObjectId = Converters.toObjectId(userId);
        User currentUser = verifyUser(userObjectId);
        verifyPasswordEquality(password, currentUser);
        return currentUser;
    }

    private User verifyUser(ObjectId userObjectId) {
        User user = userRepository.findOneById(userObjectId)
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));
        if (!securityUtils.checkIfAuthorized(userObjectId)) {
            throw new BeerJournalException(USER_FORBIDDEN_MODIFICATION);
        }
        return user;
    }

    private void verifyEmailEquality(String email, User currentUser) {
        boolean emailCorrect = email.equals(currentUser.getEmail());
        if (!emailCorrect) {
            throw new BeerJournalException(USER_INCORRECT_EMAIL);
        }
    }

    private void verifyPasswordEquality(String rawPassword, User currentUser) {
        boolean passwordCorrect = passwordEncoder.matches(rawPassword, currentUser.getPassword());
        if (!passwordCorrect) {
            throw new BeerJournalException(USER_INCORRECT_PASSWORD);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}

