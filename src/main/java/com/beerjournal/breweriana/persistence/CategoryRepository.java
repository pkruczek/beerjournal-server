package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryCrudRepository crudRepository;

    public Optional<Category> findOneByName(String category) {
        return crudRepository.findOneByName(category);
    }

    public Category save(Category category) {
        return crudRepository.save(category);
    }

}
