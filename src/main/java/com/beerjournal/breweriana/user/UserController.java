package com.beerjournal.breweriana.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody @Validated UserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(userService.getUserWithID(id), HttpStatus.OK);
    }

}
