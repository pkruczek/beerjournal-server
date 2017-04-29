package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.user.User;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserCrudRepository crudRepository;
    private final UserCollectionCrudRepository userCollectionCrudRepository;

    public ImmutableSet<User> findAll() {
        return ImmutableSet.<User>builder()
                .addAll(crudRepository.findAll())
                .build();
    }

    public Optional<User> findOneById(ObjectId objectId) {
        return crudRepository.findOneById(objectId);
    }

    public Optional<User> findOneByEmail(String email) {
        return crudRepository.findOneByEmail(email);
    }

    public User save(User user) {
        User savedUser = crudRepository.save(user);
        addEmptyCollection(savedUser.getId());
        return savedUser;
    }

    private UserCollection addEmptyCollection(ObjectId ownerId) {
        return userCollectionCrudRepository.save(UserCollection.of(ownerId, Collections.emptySet()));
    }
}
