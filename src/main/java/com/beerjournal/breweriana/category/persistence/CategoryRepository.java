package com.beerjournal.breweriana.category.persistence;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryCrudRepository crudRepository;

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
        return unifiedValues(category)
                .contains(unify(value));
    }

    private Set<Object> unifiedValues(Category category) {
        return category.getValues().stream()
                .map(this::unifyIfString)
                .collect(Collectors.toSet());
    }

    private Object unifyIfString(Object value) {
        return value instanceof String ? unify((String) value) : value;
    }

    private String unify(String value) {
        return value.toLowerCase().trim().replaceAll("\\s+", "-");
    }

}
