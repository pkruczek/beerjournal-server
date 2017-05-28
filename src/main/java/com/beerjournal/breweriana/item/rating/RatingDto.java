package com.beerjournal.breweriana.item.rating;

import com.beerjournal.breweriana.item.rating.persistence.Rating;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.beerjournal.breweriana.utils.Converters.toObjectId;
import static com.beerjournal.breweriana.utils.Converters.toStringId;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class RatingDto {

    private final String id;
    @NotEmpty
    private final String itemId;
    @NotEmpty
    private final String raterId;
    @Min(1)
    @Max(5)
    private final int value;

    public static RatingDto of(Rating rating) {
        return RatingDto.builder()
                .id(toStringId(rating.getId()))
                .itemId(toStringId(rating.getItemId()))
                .raterId(toStringId(rating.getRaterId()))
                .value(rating.getValue())
                .build();
    }

    static Rating asRating(RatingDto ratingDto) {
        return Rating.builder()
                .itemId(toObjectId(ratingDto.getItemId()))
                .raterId(toObjectId(ratingDto.getRaterId()))
                .value(ratingDto.getValue())
                .build();
    }
}
