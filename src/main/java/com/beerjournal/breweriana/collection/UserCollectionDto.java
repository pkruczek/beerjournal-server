package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.collection.persistence.UserCollection;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.breweriana.utils.Converters.toStringId;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class UserCollectionDto {

    private final String id;
    private final String ownerId;
    private final Set<ItemRefDto> itemRefs;

    static UserCollectionDto of(UserCollection userCollection) {

        Set<ItemRefDto> itemRefDtos = userCollection.getItemRefs()
                .stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());

        return UserCollectionDto.builder()
                .id(toStringId(userCollection.getId()))
                .ownerId(toStringId(userCollection.getOwnerId()))
                .itemRefs(itemRefDtos)
                .build();
    }

}
