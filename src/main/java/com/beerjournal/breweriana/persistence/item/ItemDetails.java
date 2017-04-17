package com.beerjournal.breweriana.persistence.item;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@EqualsAndHashCode(exclude = {"id"})
public class ItemDetails {

    @Id
    private final String id;
    private final String name;
    private final String category;
    private final String country;
    private final String brewery;
    private final String style;
    private final Set<Attribute> attributes;

    @PersistenceConstructor
    ItemDetails(String id, String name, String category, String country, String brewery, String style, Set<Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.country = country;
        this.brewery = brewery;
        this.style = style;
        this.attributes = attributes;
    }

    @Builder
    ItemDetails(String name, String category, String country, String brewery, String style, Set<Attribute> attributes) {
        this(null, name, category, country, brewery, style, attributes);
    }
}
