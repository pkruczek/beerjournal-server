package com.beerjournal.breweriana.category;

import com.beerjournal.breweriana.category.persistence.Category;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class CategoryDto {

    private final String id;
    private final String name;

    static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId().toHexString())
                .name(category.getName())
                .build();
    }

}
