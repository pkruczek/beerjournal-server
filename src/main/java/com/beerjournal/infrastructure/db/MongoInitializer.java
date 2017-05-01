package com.beerjournal.infrastructure.db;

import com.beerjournal.breweriana.category.persistence.Category;
import com.beerjournal.breweriana.category.persistence.CountryData;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Profile({"dev", "prod"})
@RequiredArgsConstructor
class MongoInitializer {

    private static final Set<Category> INIT_CATEGORIES = ImmutableSet.of(
            Category.of("brewery", Collections.emptySet()),
            Category.of("type", Collections.emptySet()),
            Category.of("style", Collections.emptySet()),
            Category.of("country", ImmutableSet.copyOf(countryData()))
    );


    private final MongoOperations mongoOperations;

    @EventListener(ContextRefreshedEvent.class)
    void putCategories() {
        INIT_CATEGORIES.forEach(this::ensureCategory);
    }

    private void ensureCategory(Category category) {
        mongoOperations.findAndModify(
                new Query().addCriteria(Criteria.where("name").is(category.getName())),
                new Update().pushAll("values", category.getValues().toArray()),
                new FindAndModifyOptions().upsert(true),
                Category.class);
    }

    private static Set<CountryData> countryData() {
        return Stream.of(Locale.getISOCountries())
                .map(l -> CountryData.of(l, new Locale("", l).getDisplayName()))
                .collect(Collectors.toSet());
    }

}
