package com.beerjournal.breweriana.persistence.collection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepository {

    private final UserCollectionCrudRepository crudRepository;

    public UserCollection save(UserCollection userCollection) {
        return crudRepository.save(userCollection);
    }
}
