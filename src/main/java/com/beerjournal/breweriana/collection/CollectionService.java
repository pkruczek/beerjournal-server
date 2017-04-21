package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CollectionService {

    private final UserCollectionRepository userCollectionRepository;

    UserCollection getCollectionByOwnerId(String ownerId) {
        return userCollectionRepository.findByOwnerId(ServiceUtils.stringToObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));
    }

    Item addItem(String ownerId, Item item) {
        userCollectionRepository.addNewItem(ServiceUtils.stringToObjectId(ownerId), item);
        return item;
    }

}
