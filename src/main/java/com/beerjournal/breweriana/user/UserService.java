package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.events.EventDto;
import com.beerjournal.breweriana.events.EventQueue;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventQueue eventQueue;

    Set<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toSet());
    }

    UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(toUser(userDto));
        UserDto savedUserDto = UserDto.toDto(savedUser);
        eventQueue.addEvent(EventDto.of(savedUserDto));
        return savedUserDto;
    }

    UserDto getUserWithID(String userId) {
        User user = userRepository.findOneById(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));
        return UserDto.toDto(user);
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

