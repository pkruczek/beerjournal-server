package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_FORBIDDEN_MODIFICATION;
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
        User savedUser = userRepository.save(toUser(userDto));
        return UserDto.of(savedUser);
    }

    UserDto getUserWithId(String userId) {
        User user = userRepository.findOneById(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));
        return UserDto.of(user);
    }

    UserDto modifyUserWithId(String userId, UserDto userDto) {
        ObjectId userObjectId = ServiceUtils.stringToObjectId(userId);
        userRepository.findOneById(userObjectId)
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));

        if(!securityUtils.checkIfAuthorized(userId)){
            throw new BeerJournalException(USER_FORBIDDEN_MODIFICATION);
        }

        User modifiedUser = User.copyWithAssignedId(userObjectId, toUser(userDto));
        User updatedUser = userRepository.update(modifiedUser);
        return UserDto.of(updatedUser);
    }

    UserDto deleteUserWithId(String userId) {
        ObjectId userObjectId = ServiceUtils.stringToObjectId(userId);
        userRepository.findOneById(userObjectId)
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));

        if(!securityUtils.checkIfAuthorized(userId)){
            throw new BeerJournalException(USER_FORBIDDEN_MODIFICATION);
        }

        User deletedUser = userRepository.deleteOneById(userObjectId);
        return UserDto.of(deletedUser);
    }

    private User toUser(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }

}

