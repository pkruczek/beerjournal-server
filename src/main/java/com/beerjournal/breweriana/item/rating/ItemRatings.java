package com.beerjournal.breweriana.item.rating;

import com.beerjournal.breweriana.item.rating.persistence.Rating;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Set;

@Data(staticConstructor = "of")
public class ItemRatings {
    private final ObjectId itemId;
    private final Set<Rating> ratings;
}
