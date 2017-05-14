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
            @PathVariable(value = "ownerId") String id,
            @RequestParam(value = "lacking", defaultValue = "false") boolean lacking,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "count", defaultValue = "10") int count,
            @RequestParam(value = "filterName", defaultValue = "", required = false) String filterVariableName,
            @RequestParam(value = "filterValue", defaultValue = "", required = false) String filterVariableValue) {
        Page<ItemRefDto> items = lacking ?
                collectionService.getAllNotInUserCollection(id, page, count, filterVariableName, filterVariableValue) :
                collectionService.getAllItemRefsInUserCollection(id, page, count, filterVariableName, filterVariableValue);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
