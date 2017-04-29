package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.item.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("{ownerId}/collection/items")
    ResponseEntity<ItemDto> addItemToCollection(@PathVariable(value = "ownerId") String ownerId,
                                                @RequestBody @Validated ItemDto item) {
        return new ResponseEntity<>(collectionService.addItem(ownerId, item), HttpStatus.CREATED);
    }

    @DeleteMapping("{ownerId}/collection/items/{itemId}")
    ResponseEntity<ItemDto> deleteItemFromCollection(@PathVariable(value = "ownerId") String ownerId,
                                                     @PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(collectionService.deleteItem(ownerId, itemId), HttpStatus.OK);
    }

}
