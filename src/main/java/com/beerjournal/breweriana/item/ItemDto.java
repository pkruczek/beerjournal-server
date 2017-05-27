package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.item.persistence.Attribute;
import com.beerjournal.breweriana.item.persistence.Item;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.breweriana.utils.Converters.*;
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
    @Singular private final Set<String> imageIds;
    private final double averageRating;

    public static ItemDto of(Item item) {
        return ItemDto.builder()
                .id(toStringId(item.getId()))
                .ownerId(toStringId(item.getOwnerId()))
                .name(item.getName())
                .type(item.getType())
                .country(item.getCountry())
                .brewery(item.getBrewery())
                .style(item.getStyle())
                .attributes(item.getAttributes())
                .imageIds(toStringIds(item.getImageIds()).collect(Collectors.toSet()))
                .averageRating(item.getAverageRating())
                .build();
    }

    static Item asItem(ItemDto itemDto, String ownerId) {
        return Item.builder()
                .ownerId(toObjectId(ownerId))
                .name(itemDto.getName())
                .type(itemDto.getType())
                .country(itemDto.getCountry())
                .brewery(itemDto.getBrewery())
                .style(itemDto.getStyle())
                .attributes(itemDto.getAttributes() != null ? itemDto.getAttributes() : Collections.emptySet())
                .build();
    }

}
