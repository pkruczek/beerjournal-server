package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.category.Category;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryCrudRepository crudRepository;

    public Optional<Category> findOneByName(String category) {
        return crudRepository.findOneByName(category);
    }

    public Set<Category> findAll() {
        return ImmutableSet.copyOf(crudRepository.findAll());
    }

    Category save(Category category) {
        return crudRepository.save(category);
    }

}
