package com.beerjournal.breweriana.exchange;

import lombok.Data;

@Data(staticConstructor = "of")
class ExchangeStatusDto {

    private final boolean performed;

}
