package com.beerjournal.breweriana.category;

import com.beerjournal.breweriana.persistence.CategoryRepository;
import com.beerjournal.breweriana.persistence.category.Category;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findOneByName(name)
                .orElseThrow(() -> new BeerJournalException(CATEGORY_NOT_FOUND));

        return CategoryDto.toDto(category);
    }

    Set<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::toDto)
                .collect(Collectors.toSet());
    }

}
