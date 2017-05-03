package com.beerjournal.breweriana.collection.persistence;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;

@Component
@RequiredArgsConstructor(access = PACKAGE)
class CollectionOnItemChangeUpdater implements UpdateListener<Item> {

    private final UserCollectionRepository userCollectionRepository;

    @Override
    public void onInsert(Item item) {
        userCollectionRepository.addNewItem(item);
    }

    @Override
    public void onDelete(Item item) {
        userCollectionRepository.deleteItem(item);
    }
}
