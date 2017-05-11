package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody @Validated UserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<Collection<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    ResponseEntity<UserDto> getUserWithId(@PathVariable(value = "userId") String userId) {
        return new ResponseEntity<>(userService.getUserWithId(userId), HttpStatus.OK);
    }

    @PutMapping("{userId}")
    ResponseEntity<UserDto> modifyUserWithId(@PathVariable(value = "userId") String userId,
                                             @RequestBody @Validated UserDetailsDto user) {
        return new ResponseEntity<>(userService.modifyUserWithId(userId, user), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    ResponseEntity<UserDto> deleteUserWithId(@PathVariable(value = "userId") String userId,
                                             @RequestBody @Validated UserDeleteDto user) {
        return new ResponseEntity<>(userService.deleteUserWithId(userId, user), HttpStatus.OK);
    }

    @PostMapping("{userId}/password")
    ResponseEntity<?> modifyUserPassword(@PathVariable(value = "userId") String userId,
                                         @RequestBody @Validated UserPasswordDto user) {
        userService.modifyUserPassword(userId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{userId}/email")
    ResponseEntity<?> modifyUserEmail(@PathVariable(value = "userId") String userId,
                                      @RequestBody @Validated UserEmailDto user) {
        userService.modifyUserEmail(userId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
