package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.item.persistence.Attribute;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class ItemDto {

    private final String id;

    private final String ownerId;
    @NotEmpty private final String name;
    @NotEmpty private final String type;
    @NotEmpty private final String country;
    @NotEmpty private final String brewery;
    @NotEmpty private final String style;
    @Singular private final Set<Attribute> attributes;
    @Singular private final Set<ObjectId> images;

    public static ItemDto of(Item item){
        return ItemDto.builder()
                .id(item.getId().toHexString())
                .ownerId(item.getOwnerId().toHexString())
                .name(item.getName())
                .type(item.getType())
                .country(item.getCountry())
                .brewery(item.getBrewery())
                .style(item.getStyle())
                .attributes(item.getAttributes() != null ? item.getAttributes() : new HashSet<>())
                .images(item.getImages() != null ? item.getImages() : new HashSet<>())
                .build();
    }

    static Item asItem(ItemDto itemDto, String ownerId){
        return Item.builder()
                .ownerId(ServiceUtils.stringToObjectId(ownerId))
                .name(itemDto.getName())
                .type(itemDto.getType())
                .country(itemDto.getCountry())
                .brewery(itemDto.getBrewery())
                .style(itemDto.getStyle())
                .attributes(itemDto.getAttributes() != null ? itemDto.getAttributes() : new HashSet<>())
                .images(itemDto.getImages() != null ? itemDto.getImages() : new HashSet<>())
                .build();
    }

}
