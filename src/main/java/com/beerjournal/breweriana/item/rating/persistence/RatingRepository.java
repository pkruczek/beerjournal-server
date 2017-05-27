package com.beerjournal.breweriana.item.rating.persistence;

import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.item.rating.ItemIdAndRatings;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RatingRepository {

    private final RatingCrudRepository ratingCrudRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final Set<UpdateListener<ItemIdAndRatings>> ratingUpdateListeners;

    public Set<Rating> findRatingsByItemId(ObjectId itemId) {
        return ratingCrudRepository.findAllByItemId(itemId);
    }

    public Set<Rating> findRatingsByRaterId(ObjectId raterId) {
        return ratingCrudRepository.findAllByRaterId(raterId);
    }

    public Rating save(Rating rating) {
        validateRating(rating);

        Optional<Rating> existingRating = ratingCrudRepository
                .findOneByItemIdAndRaterId(rating.getItemId(), rating.getRaterId());
        existingRating.ifPresent(ratingCrudRepository::delete);

        Rating savedRating = ratingCrudRepository.save(rating);

        notifyUpdate(savedRating.getItemId());
        return savedRating;
    }

    private void validateRating(Rating rating) {
        userRepository.findOneById(rating.getRaterId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.USER_NOT_FOUND));
        itemRepository.findOneById(rating.getItemId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }

    public Rating delete(ObjectId ratingId) {
        Rating ratingToDelete = ratingCrudRepository.findOneById(ratingId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.RATING_NOT_FOUND));
        ratingCrudRepository.delete(ratingId);

        notifyUpdate(ratingToDelete.getItemId());
        return ratingToDelete;
    }

    private void notifyUpdate(ObjectId itemId) {
        ratingUpdateListeners.forEach(listener -> {
            Set<Rating> itemRatings = ratingCrudRepository.findAllByItemId(itemId);
            listener.onUpdate(new ItemIdAndRatings(itemId, itemRatings));
        });
    }
}
