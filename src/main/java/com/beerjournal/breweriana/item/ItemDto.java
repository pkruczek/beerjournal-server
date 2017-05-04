package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.item.persistence.Attribute;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class ItemDto {

    private final String id;

    private final String ownerId;
    @NotEmpty private final String name;
    @NotEmpty private final String type;
    @NotEmpty private final String country;
    @NotEmpty private final String brewery;
    @NotEmpty private final String style;
    private final Set<Attribute> attributes;
    private final Set<String> images;

    static ItemDto toDto(Item item){
        return ItemDto.builder()
                .id(item.getId().toHexString())
                .ownerId(item.getOwnerId().toHexString())
                .name(item.getName())
                .type(item.getType())
                .country(item.getCountry())
                .brewery(item.getBrewery())
                .style(item.getStyle())
                .attributes(item.getAttributes())
                .images(item.getImages())
                .build();
    }

    static Item fromDto(ItemDto itemDto, String ownerId){
        return Item.builder()
                .ownerId(ServiceUtils.stringToObjectId(ownerId))
                .name(itemDto.getName())
                .type(itemDto.getType())
                .country(itemDto.getCountry())
                .brewery(itemDto.getBrewery())
                .style(itemDto.getStyle())
                .attributes(itemDto.getAttributes())
                .images(itemDto.getImages())
                .build();
    }

}
