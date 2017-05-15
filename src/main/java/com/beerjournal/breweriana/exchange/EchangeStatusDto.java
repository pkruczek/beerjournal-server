package com.beerjournal.breweriana.exchange;

import lombok.Data;

@Data(staticConstructor = "of")
class EchangeStatusDto {

    private final boolean accepted;

}
