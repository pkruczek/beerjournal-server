package com.beerjournal.datamodel.repository;

import com.beerjournal.datamodel.entity.UserEntity;

import java.util.Collection;

public interface UserRepository extends Repository<UserEntity> {
    Collection<UserEntity> getAllUsers();

    UserEntity getById(String id);

    UserEntity getByUsername(String username);

    void deleteUser(UserEntity entity);
}
