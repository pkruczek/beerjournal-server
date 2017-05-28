package com.beerjournal.breweriana.exchange;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data(staticConstructor = "of")
@Builder
class ExchangeUpdateDto {

    @NotNull @Singular private final Set<String> desiredItemIds;
    @NotNull @Singular private final Set<String> offeredItemIds;

}
