package com.beerjournal.breweriana.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    ResponseEntity<Collection<CategoryDto>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("{name}")
    ResponseEntity<CategoryDto> getCategoryByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(categoryService.getCategoryByName(name), HttpStatus.OK);
    }

}
