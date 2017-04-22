package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.persistence.item.Attribute;
import com.beerjournal.breweriana.persistence.item.Item;
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
public class ItemDto {

    private final String id;

    @NotEmpty private final String ownerId;
    @NotEmpty private final String name;
    @NotEmpty private final String category;
    @NotEmpty private final String country;
    @NotEmpty private final String brewery;
    @NotEmpty private final String style;
    private final Set<Attribute> attributes;

    public static ItemDto toDto(Item item){
        return ItemDto.builder()
                .id(item.getId().toHexString())
                .ownerId(item.getOwnerId().toHexString())
                .name(item.getName())
                .category(item.getCategory())
                .country(item.getCountry())
                .brewery(item.getBrewery())
                .style(item.getStyle())
                .attributes(item.getAttributes())
                .build();
    }

    public static Item fromDto(ItemDto itemDto, String ownerId){
        return Item.builder()
                .ownerId(ServiceUtils.stringToObjectId(ownerId))
                .name(itemDto.getName())
                .category(itemDto.getCategory())
                .country(itemDto.getCountry())
                .brewery(itemDto.getBrewery())
                .style(itemDto.getStyle())
                .attributes(itemDto.getAttributes())
                .build();
    }

}
