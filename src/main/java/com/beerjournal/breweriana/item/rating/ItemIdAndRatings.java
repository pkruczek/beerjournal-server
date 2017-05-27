package com.beerjournal.breweriana.item.rating;

import com.beerjournal.breweriana.item.rating.persistence.Rating;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Set;

@RequiredArgsConstructor
@Data
public class ItemIdAndRatings {
    private final ObjectId itemId;
    private final Set<Rating> ratings;
}
