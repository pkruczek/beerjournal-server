package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("{ownerId}/collections")
    ResponseEntity<UserCollection> getCollectionByOwnerId(@PathVariable(value = "ownerId") String ownerId) {
        return new ResponseEntity<>(collectionService.getCollectionByOwnerId(ownerId), HttpStatus.OK);
    }

    @PostMapping("{ownerId}/collections")
    ResponseEntity<Item> addItemToCollection(@PathVariable(value = "ownerId") String ownerId, @RequestBody Item item) {
        return new ResponseEntity<>(collectionService.addItem(ownerId, item), HttpStatus.CREATED);
    }

}
