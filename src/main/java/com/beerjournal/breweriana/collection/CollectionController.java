package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.item.ItemDto;
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
    ResponseEntity<UserCollectionDto> getCollectionByOwnerId(@PathVariable(value = "ownerId") String ownerId) {
        return new ResponseEntity<>(collectionService.getCollectionByOwnerId(ownerId), HttpStatus.OK);
    }

    @PostMapping("{ownerId}/collections")
    ResponseEntity<ItemDto> addItemToCollection(@PathVariable(value = "ownerId") String ownerId, @RequestBody ItemDto item) {
        return new ResponseEntity<>(collectionService.addItem(ownerId, item), HttpStatus.CREATED);
    }

}
