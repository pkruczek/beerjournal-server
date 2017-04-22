package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.item.ItemRefDto;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class UserCollectionDto {

    private final String id;
    private final String ownerId;
    private final Set<ItemRefDto> itemRefs;

    static UserCollectionDto toDto(UserCollection userCollection) {

        Set<ItemRefDto> itemRefDtos = userCollection.getItemRefs()
                .stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());

        return UserCollectionDto.builder()
                .id(userCollection.getId().toHexString())
                .ownerId(userCollection.getOwnerId().toHexString())
                .itemRefs(itemRefDtos)
                .build();
    }

}
