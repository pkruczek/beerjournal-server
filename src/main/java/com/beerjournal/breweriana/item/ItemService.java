package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.persistence.ItemRepository;
import com.beerjournal.breweriana.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.persistence.collection.ItemRef;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.beerjournal.infrastructure.error.ErrorInfo.ITEM_NOT_FOUND;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
class ItemService {

    private final ItemRepository itemRepository;
    private final UserCollectionRepository userCollectionRepository;

    Set<Item> getAllNotInUserCollection(String userId) {
        return itemRepository.findAllNotInUserCollection(ServiceUtils.stringToObjectId(userId));
    }

    Set<ItemRef> getAllItemRefsInUserCollection(String userId) {
        UserCollection userCollection = userCollectionRepository
                .findByOwnerId(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        return userCollection.getItemRefs();
    }

    Item getItemDetails(String id) {
        return itemRepository.findOneById(id)
                .orElseThrow(() -> new BeerJournalException(ITEM_NOT_FOUND));
    }

}
