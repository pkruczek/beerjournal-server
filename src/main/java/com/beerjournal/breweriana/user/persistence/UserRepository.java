package com.beerjournal.breweriana.user.persistence;

import com.beerjournal.breweriana.image.persistance.FileRepository;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserCrudRepository crudRepository;
    private final Set<UpdateListener<User>> userUpdateListeners;
    private final MongoOperations mongoOperations;
    private final FileRepository fileRepository;

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

    public User deleteOneById(ObjectId objectId) {
        User deletedUser = mongoOperations.findAndRemove(
                new Query(Criteria.where("Id").is(objectId)),
                User.class);
        if (deletedUser.getAvatarFileId() != null) fileRepository.deleteFileById(deletedUser.getAvatarFileId());
        userUpdateListeners.forEach(listener -> listener.onDelete(deletedUser));
        return deletedUser;
    }

    public User update(User updatedUser) {
        User savedUser = crudRepository.save(updatedUser);
        userUpdateListeners.forEach(listener -> listener.onUpdate(updatedUser));
        return savedUser;
    }

    public User save(User user) {
        User savedUser = crudRepository.save(user);
        userUpdateListeners.forEach(listener -> listener.onInsert(user));
        return savedUser;
    }

}
