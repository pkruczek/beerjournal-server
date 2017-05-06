package com.beerjournal.breweriana.category.persistence;

import lombok.Data;

@Data(staticConstructor = "of")
public final class CountryData {

    private final String code;
    private final String name;

}
