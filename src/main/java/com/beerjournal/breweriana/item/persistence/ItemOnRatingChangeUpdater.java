package com.beerjournal.breweriana.item.persistence;

import com.beerjournal.breweriana.item.rating.ItemRatings;
import com.beerjournal.breweriana.item.rating.persistence.Rating;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;

@Component
@RequiredArgsConstructor(access = PACKAGE)
public class ItemOnRatingChangeUpdater implements UpdateListener<ItemRatings> {

    private final ItemRepository itemRepository;

    @Override
    public void onUpdate(ItemRatings itemRatings) {
        Item itemToUpdate = itemRepository.findOneById(itemRatings.getItemId())
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));

        double average = itemRatings.getRatings().stream()
                .mapToDouble(Rating::getValue).average()
                .orElse(0.0);

        itemRepository.save(itemToUpdate.withAverageRating(average));
    }
}
