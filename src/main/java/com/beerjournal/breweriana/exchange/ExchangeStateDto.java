package com.beerjournal.breweriana.exchange;

import lombok.Data;

@Data(staticConstructor = "of")
class ExchangeStateDto {

    private final String state;

}
