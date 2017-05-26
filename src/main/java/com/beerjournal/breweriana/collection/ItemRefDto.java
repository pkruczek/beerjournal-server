package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import lombok.Builder;
import lombok.Data;

import static com.beerjournal.breweriana.utils.Converters.toStringId;

@Data
@Builder
public class ItemRefDto {

    private final String itemId;
    private final String name;
    private final String category;

    public static ItemRefDto toDto(ItemRef itemRef){
        return ItemRefDto.builder()
                .itemId(toStringId(itemRef.getItemId()))
                .name(itemRef.getName())
                .category(itemRef.getType())
                .build();
    }

}
