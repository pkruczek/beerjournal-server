package com.beerjournal.breweriana.category.persistence;

import lombok.Data;

@Data(staticConstructor = "of")
public class CountryData {

    private final String code;
    private final String name;

}
