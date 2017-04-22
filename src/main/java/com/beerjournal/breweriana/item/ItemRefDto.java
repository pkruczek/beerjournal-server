package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.persistence.collection.ItemRef;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRefDto {

    private final String name;
    private final String category;

    public static ItemRefDto toDto(ItemRef itemRef){
        return ItemRefDto.builder()
                .name(itemRef.getName())
                .category(itemRef.getCategory())
                .build();
    }

}
