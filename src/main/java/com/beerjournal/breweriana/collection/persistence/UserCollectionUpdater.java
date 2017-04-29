package com.beerjournal.breweriana.collection.persistence;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemUpdateListener;
import com.beerjournal.breweriana.user.User;
import com.beerjournal.breweriana.user.persistence.UserUpdateListener;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static lombok.AccessLevel.PACKAGE;

@Component
@AllArgsConstructor(access = PACKAGE)
class UserCollectionUpdater implements UserUpdateListener, ItemUpdateListener {

    private UserCollectionRepository userCollectionRepository;

    @Override
    public void onInsert(User user) {
        addEmptyCollection(user.getId());
    }

    @Override
    public void onInsert(Item item) {
        userCollectionRepository.addNewItem(item);
    }

    private UserCollection addEmptyCollection(ObjectId ownerId) {
        return userCollectionRepository.save(UserCollection.of(ownerId, Collections.emptySet()));
    }
}
