package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.collection.ItemRefDto;
import com.beerjournal.breweriana.collection.persistence.ItemRef;
import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.breweriana.utils.Converters.toStringId;
import static com.beerjournal.breweriana.utils.Converters.toStringState;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class ExchangeOfferDetailsDto {

    private final String id;
    private final String offerorId;
    private final String ownerId;
    @Singular private final Set<ItemRefDto> desiredItems;
    @Singular private final Set<ItemRefDto> offeredItems;
    private final String state;
    private final LocalDateTime created;

    static ExchangeOfferDetailsDto of(ExchangeOffer offer) {
        return builder()
                .id(toStringId(offer.getId()))
                .offerorId(toStringId(offer.getOfferorId()))
                .ownerId(toStringId(offer.getOwnerId()))
                .desiredItems(toItemRefDtos(offer.getDesiredItems()))
                .offeredItems(toItemRefDtos(offer.getOfferedItems()))
                .state(toStringState(offer.getState()))
                .created(offer.getCreated())
                .build();
    }

    private static Set<ItemRefDto> toItemRefDtos(Collection<ItemRef> itemRefs) {
        return itemRefs.stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());
    }

}
