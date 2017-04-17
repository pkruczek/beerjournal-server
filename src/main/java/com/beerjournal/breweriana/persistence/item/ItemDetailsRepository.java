package com.beerjournal.breweriana.persistence.item;

import com.beerjournal.breweriana.persistence.category.Category;
import com.beerjournal.breweriana.persistence.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemDetailsRepository {

    private final ItemDetailsCrudRepository crudRepository;
    private final CategoryRepository categoryRepository;

    public ItemDetails save(ItemDetails itemDetails) {
        ensureCategory(itemDetails.getCategory());
        return crudRepository.save(itemDetails);
    }

    private void ensureCategory(String category) {
        Optional<Category> maybeCategory = categoryRepository.findOneByName(category);
        if (!maybeCategory.isPresent()) {
            categoryRepository.save(Category.of(category));
        }
    }
}
