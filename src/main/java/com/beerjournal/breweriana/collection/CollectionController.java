package com.beerjournal.breweriana.collection;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("{ownerId}/collection")
    ResponseEntity<UserCollectionDto> getCollectionByOwnerId(@PathVariable(value = "ownerId") String ownerId) {
        return new ResponseEntity<>(collectionService.getCollectionByOwnerId(ownerId), HttpStatus.OK);
    }

    @GetMapping("{ownerId}/collection/items")
    ResponseEntity<Collection<ItemRefDto>> getAllItemRefsInUserCollection(
            @RequestParam(value = "lacking", defaultValue = "false") boolean lacking,
            @PathVariable(value = "ownerId") String id) {
        Set<ItemRefDto> items = lacking ? collectionService.getAllNotInUserCollection(id) : collectionService.getAllItemRefsInUserCollection(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("{ownerId}/collection/items/{itemId}")
    ResponseEntity<ItemDto> deleteItemFromCollection(@PathVariable(value = "ownerId") String ownerId,
                                                     @PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(collectionService.deleteItem(ownerId, itemId), HttpStatus.OK);
    }

}
