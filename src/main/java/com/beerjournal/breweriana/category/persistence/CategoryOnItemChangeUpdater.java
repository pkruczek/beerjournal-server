package com.beerjournal.breweriana.category.persistence;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;

@Component
@RequiredArgsConstructor(access = PACKAGE)
class CategoryOnItemChangeUpdater implements UpdateListener<Item> {

    private final CategoryRepository categoryRepository;

    @Override
    public void onInsert(Item item) {
        ensureCategories(item);
    }

    private void ensureCategories(Item item) {
        categories(item)
                .forEach(categoryRepository::ensureCategory);
    }

    private ImmutableMap<String, String> categories(Item item) {
        return ImmutableMap.<String, String>builder()
                .put("type", item.getType())
                .put("country", item.getCountry())
                .put("style", item.getStyle())
                .put("brewery", item.getBrewery())
                .build();
    }

}
