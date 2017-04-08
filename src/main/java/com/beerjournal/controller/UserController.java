package com.beerjournal.controller;

import com.beerjournal.datamodel.entity.UserEntity;
import com.beerjournal.datamodel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public Collection<UserEntity> getUsers() {
        return userRepository.getAllUsers();
    }

    @GetMapping("{id}")
    public UserEntity getUser(@PathVariable("id") String id) {
        return userRepository.getById(id);
    }

    @DeleteMapping("")
    public void deleteUsers() {
        userRepository.deleteAll();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userRepository.deleteUser(userRepository.getById(id));
    }

    @PostMapping("")
    public void addUser(@RequestBody UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @PutMapping("")
    public void updateUser(@RequestBody UserEntity userEntity) {
        userRepository.update(userEntity);
    }
}
