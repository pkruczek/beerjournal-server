package com.beerjournal.breweriana.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class ItemController {

    private final ItemService itemService;

    @GetMapping("users/{id}/not_owned_items")
    ResponseEntity<Collection<ItemDto>> getAllNotInUserCollection(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.getAllNotInUserCollection(id), HttpStatus.OK);
    }

    @GetMapping("users/{id}/owned_items")
    ResponseEntity<Collection<ItemRefDto>> getAllItemRefsInUserCollection(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.getAllItemRefsInUserCollection(id), HttpStatus.OK);
    }

    @GetMapping("items/{id}")
    ResponseEntity<ItemDto> getItemDetails(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.getItemDetails(id), HttpStatus.OK);
    }

}
