package com.beerjournal.breweriana.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class ItemController {

    private final ItemService itemService;

    @GetMapping("users/{userId}/collection/items")
    ResponseEntity<Collection<ItemRefDto>> getAllNotInUserCollection(@RequestParam(value="lacking", defaultValue="false") boolean lacking,  @PathVariable(value = "userId") String id) {
        Set<ItemRefDto> items = lacking ? itemService.getAllNotInUserCollection(id) : itemService.getAllItemRefsInUserCollection(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("items/{itemId}")
    ResponseEntity<ItemDto> getItemDetails(@PathVariable(value = "itemId") String id) {
        return new ResponseEntity<>(itemService.getItemDetails(id), HttpStatus.OK);
    }

}
