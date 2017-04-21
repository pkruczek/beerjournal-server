package com.beerjournal.breweriana.user;

import com.beerjournal.breweriana.persistence.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(userService.getUserWithID(id), HttpStatus.OK);
    }

}
