package com.beerjournal.controller;

import com.beerjournal.datamodel.entity.UserCollectionEntity;
import com.beerjournal.datamodel.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "collections")
public class CollectionController {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @GetMapping("")
    public Collection<UserCollectionEntity> getCollections() {
        return userCollectionRepository.getAll();
    }

    @GetMapping("{id}")
    public UserCollectionEntity getCollection(@PathVariable("id") String id) {
        return userCollectionRepository.getById(id);
    }

    @GetMapping("{id}/image")
    public Optional<BufferedImage> getImageForCollection(@PathVariable("id") String id) {
        return userCollectionRepository.getImageForUserCollection(id);
    }

    @DeleteMapping("")
    public void deleteCollections() {
        userCollectionRepository.deleteAll();
    }

    @DeleteMapping("{id}")
    public void deleteCollection(@PathVariable("id") String id) {
        userCollectionRepository.deleteWholeCollection(userCollectionRepository.getById(id));
    }


    @PostMapping("")
    public void addCollection(@RequestBody UserCollectionEntity userCollectionEntity) {
        userCollectionRepository.save(userCollectionEntity);
    }

    @PutMapping("")
    public void updateCollection(@RequestBody UserCollectionEntity userEntity) {
        userCollectionRepository.update(userEntity);
    }

}
