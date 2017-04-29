package com.beerjournal.breweriana.category.persistence;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemUpdateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;

@Component
@RequiredArgsConstructor(access = PACKAGE)
class CategoryUpdater implements ItemUpdateListener {

    private final CategoryCrudRepository categoryCrudRepository;

    @Override
    public void onInsert(Item item) {
        ensureCategory(item.getCategory());
    }

    private void ensureCategory(String category) {
        Optional<Category> maybeCategory = categoryCrudRepository.findOneByName(category);
        if (!maybeCategory.isPresent()) {
            categoryCrudRepository.save(Category.of(category));
        }
    }


}
