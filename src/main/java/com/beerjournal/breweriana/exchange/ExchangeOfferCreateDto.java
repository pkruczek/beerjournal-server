package com.beerjournal.breweriana.exchange;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data(staticConstructor = "of")
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class ExchangeOfferCreateDto {

    @NotNull private final String offerorId;
    @NotNull private final String ownerId;
    @NotNull @Singular private final Set<String> desiredItemIds;
    @NotNull @Singular private final Set<String> offeredItemIds;

}
