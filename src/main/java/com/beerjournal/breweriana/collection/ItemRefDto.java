package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.persistence.collection.ItemRef;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRefDto {

    private final String itemId;
    private final String name;
    private final String category;

    public static ItemRefDto toDto(ItemRef itemRef){
        return ItemRefDto.builder()
                .itemId(itemRef.getItemId().toHexString())
                .name(itemRef.getName())
                .category(itemRef.getCategory())
                .build();
    }

}
