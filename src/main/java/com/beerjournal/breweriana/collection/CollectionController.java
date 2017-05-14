package com.beerjournal.breweriana.collection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<Page<ItemRefDto>> getAllItemRefsInUserCollection(
            @RequestParam(value = "lacking", defaultValue = "false") boolean lacking,
            @PathVariable(value = "ownerId") String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "count", defaultValue = "10") int count) {
        Page<ItemRefDto> items = lacking ? collectionService.getAllNotInUserCollection(id, page, count) : collectionService.getAllItemRefsInUserCollection(id, page, count);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
