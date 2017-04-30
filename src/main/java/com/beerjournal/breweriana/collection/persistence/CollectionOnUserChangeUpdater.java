package com.beerjournal.breweriana.collection.persistence;


import com.beerjournal.breweriana.user.User;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static lombok.AccessLevel.PACKAGE;

@Component
@RequiredArgsConstructor(access = PACKAGE)
class CollectionOnUserChangeUpdater implements UpdateListener<User> {

    private final UserCollectionRepository userCollectionRepository;

    @Override
    public void onInsert(User user) {
        addEmptyCollection(user.getId());
    }


    private UserCollection addEmptyCollection(ObjectId ownerId) {
        return userCollectionRepository.save(UserCollection.of(ownerId, Collections.emptySet()));
    }

}
