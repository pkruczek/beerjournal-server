package com.beerjournal.breweriana.category.persistence;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryCrudRepository crudRepository;
    private final MongoOperations mongoOperations;

    public Optional<Category> findOneByName(String category) {
        return crudRepository.findOneByName(category);
    }

    public ImmutableSet<Category> findAll() {
        return ImmutableSet.<Category>builder()
                .addAll(crudRepository.findAll())
                .build();
    }

    void ensureCategory(String name, String value) {
        Category category = crudRepository.findOneByName(name)
                .orElse(Category.of(name, Collections.emptySet()));

        if (!containsValue(category, value)) {
            crudRepository.save(category.withNewValue(value));
        }
    }

    private boolean containsValue(Category category, String value) {
        return category.getValues().stream()
                .map(this::unifyIfString)
                .collect(Collectors.toSet())
                .contains(unify(value));
    }

    private Object unifyIfString(Object value) {
        if(value instanceof String) {
            return unify((String) value);
        }
        return value;
    }

    private String unify(String value) {
        return value.toLowerCase().trim().replaceAll("\\s+", "-");
    }

}
