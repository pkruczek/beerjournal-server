package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import com.beerjournal.breweriana.utils.Converters;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRefDto {

    private final String itemId;
    private final String name;
    private final String type;
    private final String imageId;

    public static ItemRefDto toDto(ItemRef itemRef){
        return ItemRefDto.builder()
                .itemId(Converters.toStringId(itemRef.getItemId()))
                .name(itemRef.getName())
                .type(itemRef.getType())
                .imageId(Converters.toStringId(itemRef.getImageId()))
                .build();
    }

}
