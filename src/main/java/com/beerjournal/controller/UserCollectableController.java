package com.beerjournal.controller;

import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "users/{id}")
public class UserCollectableController {

    @Autowired
    private CollectableObjectsRepository collectableObjectsRepository;

    @GetMapping("collectables")
    public Collection<CollectableObjectEntity> getCollectablesForUser(@PathVariable("id") String id) {
        return collectableObjectsRepository.getAllObjectsForUser(id);
    }
}
