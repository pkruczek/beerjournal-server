package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.breweriana.utils.Converters.toObjectId;
import static com.beerjournal.infrastructure.error.ErrorInfo.*;

@Service
@RequiredArgsConstructor
class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    ItemDto getItemDetails(String id) {
        Item item = itemRepository.findOneById(toObjectId(id))
                .orElseThrow(() -> new BeerJournalException(ITEM_NOT_FOUND));

        return ItemDto.of(item);
    }

    ItemDto addItem(String ownerId, ItemDto itemDto) {
        verifyUser(ownerId);

        Item item = ItemDto.asItem(itemDto, ownerId);
        Item savedItem = itemRepository.save(item);
        return ItemDto.of(savedItem);
    }

    ItemDto deleteItem(String ownerId, String itemId) {
        verifyUser(ownerId);
        verifyItem(ownerId, itemId);

        Item deletedItem = itemRepository.delete(toObjectId(itemId));
        return ItemDto.of(deletedItem);
    }

    ItemDto updateItem(String ownerId, String itemId, ItemDto itemDto) {
        verifyUser(ownerId);
        Item item = verifyItem(ownerId, itemId);

        Item itemToUpdate = ItemDto.asItem(itemDto, ownerId)
                .withId(toObjectId(itemId))
                .withAverageRating(item.getAverageRating())
                .withImageIds(item.getImageIds())
                .withMainImageId(item.getMainImageId())
                .withCreated(item.getCreated());

        Item updatedItem = itemRepository.update(itemToUpdate);
        return ItemDto.of(updatedItem);
    }

    private User verifyUser(String ownerId) {
        User user = userRepository.findOneById(toObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND_BY_ID));
        if (!securityUtils.checkIfAuthorized(ownerId)){
            throw new BeerJournalException(COLLECTION_FORBIDDEN_MODIFICATION);
        }
        return user;
    }

    private Item verifyItem(String ownerId, String itemId) {
        Item item = itemRepository.findOneById(toObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ITEM_NOT_FOUND));
        if (!item.getOwnerId().equals(toObjectId(ownerId))) {
            throw new BeerJournalException(COLLECTION_FORBIDDEN_MODIFICATION);
        }
        return item;
    }

}
