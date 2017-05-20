package com.beerjournal.breweriana.item.rating.persistence;

import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RatingRepository {

    private final RatingCrudRepository ratingCrudRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public Set<Rating> findRatingsByItemId(ObjectId itemId) {
        return ratingCrudRepository.findAll().stream()
                .filter(rating -> rating.getItemId().equals(itemId))
                .collect(Collectors.toSet());
    }

    public Set<Rating> findRatingsByRaterId(ObjectId raterId) {
        return ratingCrudRepository.findAll().stream()
                .filter(rating -> rating.getRaterId().equals(raterId))
                .collect(Collectors.toSet());
    }

    public Rating save(Rating rating) {
        validateRating(rating);

        Optional<Rating> existingRating = findRating(rating.getItemId(), rating.getRaterId());
        existingRating.ifPresent(ratingCrudRepository::delete);

        return ratingCrudRepository.save(rating);
    }

    private void validateRating(Rating rating) {
        userRepository.findOneById(rating.getRaterId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_NOT_FOUND));
        itemRepository.findOneById(rating.getItemId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }

    private Optional<Rating> findRating(ObjectId itemId, ObjectId raterId) {
        return findRatingsByItemId(itemId).stream()
                .filter(rating -> rating.getRaterId().equals(raterId))
                .findAny();
    }

    public Rating delete(ObjectId ratingId) {
        Rating ratingToDelete = ratingCrudRepository.findOneById(ratingId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.RATING_NOT_FOUND));
        ratingCrudRepository.delete(ratingId);
        return ratingToDelete;
    }
}
