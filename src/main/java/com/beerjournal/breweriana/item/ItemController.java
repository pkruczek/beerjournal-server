package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.persistence.collection.ItemRef;
import com.beerjournal.breweriana.persistence.item.Item;
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
public class ItemController {

    private final ItemService itemService;

    @GetMapping("users/{id}/not_owned_items")
    public ResponseEntity<Collection<Item>> getAllNotInUserCollection(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.getAllNotInUserCollection(id), HttpStatus.OK);
    }

    @GetMapping("users/{id}/owned_items")
    public ResponseEntity<Collection<ItemRef>> getAllItemRefsInUserCollection(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.getAllItemRefsInUserCollection(id), HttpStatus.OK);
    }

    @GetMapping("items/{id}")
    public ResponseEntity<Item> getItemDetails(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(itemService.getItemDetails(id), HttpStatus.OK);
    }

}
