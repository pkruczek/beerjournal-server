package com.beerjournal.breweriana.category;

import com.beerjournal.breweriana.category.persistence.Category;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class CategoryDto {

    private final String name;
    private final Set<Object> values;

    static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .values(category.getValues())
                .build();
    }

}
