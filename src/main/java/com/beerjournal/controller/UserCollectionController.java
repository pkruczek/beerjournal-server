package com.beerjournal.controller;

import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "users/{id}")
public class UserCollectionController {

    @Autowired
    UserCollectionRepository userCollectionRepository;

    @GetMapping("collections")
    public Collection<UserCollectionEntity> getCollectionsForUser(@PathVariable("id") String id) {
        return userCollectionRepository.getUserCollections(id);
    }
}
