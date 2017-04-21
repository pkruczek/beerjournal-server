package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.user.User;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserCrudRepository crudRepository;
    private final UserCollectionRepository userCollectionRepository;

    public Optional<User> findOneById(ObjectId id) {
        return crudRepository.findOneById(id);
    }

    public User save(User user) {
        User savedUser = crudRepository.save(user);

        userCollectionRepository.save(UserCollection
                .builder()
                .ownerId(savedUser.getId())
                .itemRefs(Sets.newHashSet())
                .build());

        return savedUser;
    }

}
