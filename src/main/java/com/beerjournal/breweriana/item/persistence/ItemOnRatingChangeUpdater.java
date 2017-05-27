package com.beerjournal.breweriana.item.persistence;

import com.beerjournal.breweriana.item.rating.ItemIdAndRatings;
import com.beerjournal.breweriana.item.rating.persistence.Rating;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;

@Component
@RequiredArgsConstructor(access = PACKAGE)
public class ItemOnRatingChangeUpdater implements UpdateListener<ItemIdAndRatings> {

    private final ItemRepository itemRepository;

    @Override
    public void onUpdate(ItemIdAndRatings itemIdAndRatings) {
        Item itemToUpdate = itemRepository.findOneById(itemIdAndRatings.getItemId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));

        double average = itemIdAndRatings.getRatings().stream()
                .mapToDouble(Rating::getValue).average()
                .orElse(0.0);

        itemRepository.save(itemToUpdate.withAverageRating(average));
    }
}
