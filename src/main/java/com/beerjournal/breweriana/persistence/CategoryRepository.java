package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.category.Category;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryCrudRepository crudRepository;

    public Optional<Category> findByName(String category) {
        return crudRepository.findOneByName(category);
    }

    public ImmutableSet<Category> findAll() {
        return ImmutableSet.<Category>builder()
                .addAll(crudRepository.findAll())
                .build();
    }

}
