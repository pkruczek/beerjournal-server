package com.beerjournal.controller;


import com.beerjournal.datamodel.entity.CollectableObjectEntity;
import com.beerjournal.datamodel.repository.CollectableObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "collectables")
public class CollectableController {

    @Autowired
    private CollectableObjectsRepository collectableObjectsRepository;

    @GetMapping("")
    public Collection<CollectableObjectEntity> getCollectables() {
        return collectableObjectsRepository.getAll();
    }

    @GetMapping("{id}")
    public CollectableObjectEntity getCollectable(@PathVariable("id") String id) {
        return collectableObjectsRepository.getById(id);
    }

    @GetMapping("{id}/images")
    public Optional<BufferedImage> getImage(@PathVariable("id") String id) {
        return collectableObjectsRepository.getImageForObject(id);
    }

    @GetMapping("brewery/{name}")
    public Collection<CollectableObjectEntity> getByBrewery(@PathVariable("name") String name){
        return collectableObjectsRepository.getByBrewery(name);
    }

    @DeleteMapping("")
    public void deleteCollectables() {
        collectableObjectsRepository.deleteAll();
    }

    @DeleteMapping("{id}")
    public void deleteCollectable(@PathVariable("id") String id) {
        collectableObjectsRepository.delete(collectableObjectsRepository.getById(id));
    }

    @PostMapping("")
    public void addCollectable(@RequestBody CollectableObjectEntity collectableObjectEntity) {
        collectableObjectsRepository.save(collectableObjectEntity);
    }

    @PostMapping("{id}/images")
    public void addImage(@PathVariable("id") String id, @RequestBody InputStream image) {
        collectableObjectsRepository.saveImageForObject(collectableObjectsRepository.getById(id), image);
    }

    @PutMapping("")
    public void updateCollectable(@RequestBody CollectableObjectEntity collectableObjectEntity) {
        collectableObjectsRepository.update(collectableObjectEntity);
    }
}
