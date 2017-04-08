package com.beerjournal.datamodel.repository.mongo;

import com.beerjournal.datamodel.entity.UserEntity;
import com.beerjournal.datamodel.model.User;
import com.beerjournal.datamodel.repository.UserRepository;
import com.beerjournal.datamodel.repository.mongo.access.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMongoRepository repository;

    @Override
    public Collection<UserEntity> getAll() {
        return repository
                .findAll().stream()
                .map(this::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void delete(UserEntity objectToDelete) {
        repository.delete(objectToDelete.getId().get());
    }

    @Override
    public void save(UserEntity objectToSave) {
        User user = new User(
                objectToSave.getUsername(),
                objectToSave.getPassword(),
                objectToSave.getRoles());

        repository.save(user);
    }

    @Override
    public void update(UserEntity objectToUpdate) {
        if (!objectToUpdate.getId().isPresent()) {
            save(objectToUpdate);
            return;
        }

        User user = repository.findById(objectToUpdate.getId().get());

        user.username = objectToUpdate.getUsername();
        user.password = objectToUpdate.getPassword();
        user.roles = objectToUpdate.getRoles();

        repository.save(user);
    }

    @Override
    public Collection<UserEntity> getAllUsers() {
        return getAll();
    }

    @Override
    public UserEntity getById(String id) {
        User user = repository.findById(id);
        return getEntity(user);
    }

    @Override
    public UserEntity getByUsername(String username) {
        User user = repository
                .findByUsername(username).stream()
                .findFirst()
                .orElse(null);

        return getEntity(user);
    }

    @Override
    public void deleteUser(UserEntity entity) {
        delete(entity);
    }

    private UserEntity getEntity(User user) {
        if (user != null) {
            return new UserEntity(user.id, user.username, user.password, user.roles);
        } else {
            return null;
        }
    }
}
