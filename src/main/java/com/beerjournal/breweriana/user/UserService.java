package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.persistence.UserRepository;
import com.beerjournal.breweriana.persistence.user.User;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;

    Set<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::toProtectedDto)
                .collect(Collectors.toSet());
    }

    UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(UserDto.fromDto(userDto));
        return UserDto.toDto(savedUser);
    }

    UserDto getUserWithID(String userId) {
        User user = userRepository.findOneById(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));
        return UserDto.toDto(user);
    }

}

