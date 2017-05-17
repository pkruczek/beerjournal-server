package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.collection.ItemRefDto;
import com.beerjournal.breweriana.exchange.persitence.ExchangeItemOffer;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.breweriana.utils.Converters.toStringId;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class ExchangeItemOfferDetailsDto {

    private final String id;
    private final String offerorId;
    private final String ownerId;
    private final ItemRefDto desiredItem;
    @Singular  private final Set<ItemRefDto> offeredItems;
    private final boolean performed;

    static ExchangeItemOfferDetailsDto of(ExchangeItemOffer offer) {
        Set<ItemRefDto> offeredItems = offer.getOfferedItems()
                .stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());

        return builder()
                .id(toStringId(offer.getId()))
                .offerorId(toStringId(offer.getOfferorId()))
                .ownerId(toStringId(offer.getOwnerId()))
                .desiredItem(ItemRefDto.toDto(offer.getDesiredItem()))
                .offeredItems(offeredItems)
                .performed(offer.isPerformed())
                .build();
    }

}
