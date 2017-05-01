package com.beerjournal.breweriana.user.persistence;

import com.beerjournal.breweriana.user.User;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserCrudRepository crudRepository;
    private final Set<UpdateListener<User>> userUpdateListeners;

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

    public Optional<User> deleteOneById(ObjectId objectId) {
        //// TODO: 2017-04-28 should we also delete usercollection/items?
        return crudRepository.deleteOneById(objectId);
    }

    public User save(User user) {
        User savedUser = crudRepository.save(user);
        userUpdateListeners.forEach(listener -> listener.onInsert(user));
        return savedUser;
    }

}
