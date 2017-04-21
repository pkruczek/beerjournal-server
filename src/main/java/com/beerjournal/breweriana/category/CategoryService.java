package com.beerjournal.breweriana.category;

import com.beerjournal.breweriana.persistence.CategoryRepository;
import com.beerjournal.breweriana.persistence.category.Category;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.beerjournal.infrastructure.error.ErrorInfo.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CategoryService {

    private final CategoryRepository categoryRepository;

    Category getCategoryByName(String name) {
        return categoryRepository.findOneByName(name)
                .orElseThrow(() -> new BeerJournalException(CATEGORY_NOT_FOUND));
    }

    Set<Category> getCategories() {
        return categoryRepository.findAll();
    }

}
