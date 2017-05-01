package com.beerjournal.breweriana.category.persistence;

import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        mongoOperations.findAndModify(
                new Query().addCriteria(Criteria.where("name").is(name)),
                new Update().push("values", value),
                new FindAndModifyOptions().upsert(true),
                Category.class
        );
    }

}
