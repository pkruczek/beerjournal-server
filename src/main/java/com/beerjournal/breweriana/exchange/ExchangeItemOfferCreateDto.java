package com.beerjournal.breweriana.exchange;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor(access = PRIVATE)
class ExchangeItemOfferCreateDto {

    @NotNull private final String offerorId;
    @NotNull private final String ownerId;
    @NotNull private final String desiredItemId;
    @NotNull private final Set<String> offeredItemIds;

}
