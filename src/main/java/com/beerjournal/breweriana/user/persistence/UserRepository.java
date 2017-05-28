package com.beerjournal.breweriana.user.persistence;

import com.beerjournal.breweriana.file.persistence.FileRepository;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<User> findByFirstNameStartsWithAndLastNameStartsWith(String firstName,
                                                                     String lastName,
                                                                     int page,
                                                                     int count,
                                                                     String sortBy,
                                                                     String sortType) {
        Sort sort = sortBy.matches("firstName|lastName") ?
                new Sort(new Sort.Order(sortType.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy)) :
                new Sort(new Sort.Order(sortType.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "_id" ));

        return crudRepository.findByFirstNameStartsWithAndLastNameStartsWith(firstName, lastName, new PageRequest(page, count, sort));
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
        deleteAvatarIfExists(deletedUser);
        notifyDelete(deletedUser);
        return deletedUser;
    }

    public User update(User updatedUser) {
        User savedUser = crudRepository.save(updatedUser);
        notifyUpdate(updatedUser);
        return savedUser;
    }

    public User save(User user) {
        User savedUser = crudRepository.save(user);
        notifyInsert(user);
        return savedUser;
    }

    private void deleteAvatarIfExists(User deletedUser) {
        if (deletedUser.getAvatarFileId() != null) {
            fileRepository.deleteFileById(deletedUser.getAvatarFileId());
        }
    }

    private void notifyDelete(User deletedUser) {
        userUpdateListeners.forEach(listener -> listener.onDelete(deletedUser));
    }

    private void notifyUpdate(User updatedUser) {
        userUpdateListeners.forEach(listener -> listener.onUpdate(updatedUser));
    }

    private void notifyInsert(User user) {
        userUpdateListeners.forEach(listener -> listener.onInsert(user));
    }

}
