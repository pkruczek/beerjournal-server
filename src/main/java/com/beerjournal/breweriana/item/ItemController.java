package com.beerjournal.breweriana.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class ItemController {

    private final ItemService itemService;

    @GetMapping("items/{itemId}")
    ResponseEntity<ItemDto> getItemDetails(@PathVariable(value = "itemId") String id) {
        return new ResponseEntity<>(itemService.getItemDetails(id), HttpStatus.OK);
    }

    @PostMapping("users/{ownerId}/collection/items")
    ResponseEntity<ItemDto> addItemToCollection(@PathVariable(value = "ownerId") String ownerId,
                                                @RequestBody @Validated ItemDto item) {
        return new ResponseEntity<>(itemService.addItem(ownerId, item), HttpStatus.CREATED);
    }


    @DeleteMapping("users/{ownerId}/collection/items/{itemId}")
    ResponseEntity<ItemDto> deleteItemFromCollection(@PathVariable(value = "ownerId") String ownerId,
                                                     @PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(itemService.deleteItem(ownerId, itemId), HttpStatus.OK);
    }

    @PutMapping("users/{ownerId}/collection/items/{itemId}")
    ResponseEntity<ItemDto> updateItemInCollection(@PathVariable(value = "ownerId") String ownerId,
                                                   @PathVariable(value = "itemId") String itemId,
                                                   @RequestBody @Validated ItemDto updatedItem) {
        return new ResponseEntity<>(itemService.updateItem(ownerId, itemId, updatedItem), HttpStatus.OK);
    }
}
