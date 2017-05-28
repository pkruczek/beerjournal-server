package com.beerjournal.breweriana.item.rating;

import com.beerjournal.breweriana.item.rating.persistence.Rating;
import com.beerjournal.breweriana.item.rating.persistence.RatingRepository;
import com.beerjournal.breweriana.utils.Converters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class RatingService {

    private final RatingRepository ratingRepository;

    Set<RatingDto> getItemRatings(String itemId) {
        return ratingRepository.findRatingsByItemId(Converters.toObjectId(itemId))
                .stream()
                .map(RatingDto::of)
                .collect(Collectors.toSet());
    }

    Set<RatingDto> getUserRatings(String userId) {
        return ratingRepository.findRatingsByRaterId(Converters.toObjectId(userId))
                .stream()
                .map(RatingDto::of)
                .collect(Collectors.toSet());
    }

    RatingDto addItemRating(RatingDto ratingDto) {
        Rating rating = RatingDto.asRating(ratingDto);
        Rating savedRating = ratingRepository.save(rating);
        return RatingDto.of(savedRating);
    }

    RatingDto deleteRating(String ratingId) {
        Rating deletedRating = ratingRepository.delete(Converters.toObjectId(ratingId));
        return RatingDto.of(deletedRating);
    }
}
