package com.beerjournal.breweriana.collection.persistence;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.mongodb.WriteResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepository {

    private final UserCollectionCrudRepository crudRepository;
    private final MongoOperations mongoOperations;

    public Optional<UserCollection> findOneByOwnerId(ObjectId ownerId) {
        return crudRepository.findOneByOwnerId(ownerId);
    }

    public Set<ItemRef> findAllNotInUserCollection(ObjectId ownerId) {
        UserCollection userCollection = crudRepository.findOneByOwnerId(ownerId)
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        Set<String> userItemsNames = userCollection.getItemRefs().stream()
                .map(ItemRef::getName)
                .collect(Collectors.toSet());

        return findAllNotIn(userItemsNames)
                .stream()
                .map(Item::asItemRef)
                .collect(Collectors.toSet());
    }

    UserCollection deleteOneByOwnerId(ObjectId ownerId) {
        findOneByOwnerId(ownerId)
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        return mongoOperations.findAndRemove(
                new Query(Criteria.where("ownerId").is(ownerId)),
                UserCollection.class);
    }

    UserCollection save(UserCollection userCollection) {
        return crudRepository.save(userCollection);
    }

    int addNewItem(Item item) {
        ItemRef itemRef = item.asItemRef();

        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(item.getOwnerId())),
                new Update().push("itemRefs", itemRef),
                UserCollection.class);

        return writeResult.getN();
    }

    int deleteItem(Item item) {
        ItemRef itemRef = item.asItemRef();

        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(item.getOwnerId())),
                new Update().pull("itemRefs", itemRef),
                UserCollection.class);

        return writeResult.getN();
    }

    int updateItem(Item item) {
        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(new Criteria().andOperator(
                        Criteria.where("ownerId").is(item.getOwnerId()),
                        Criteria.where("itemRefs").elemMatch(Criteria.where("itemId").is(item.getId()))
                )),
                new Update().set("itemRefs.$.name", item.getName())
                            .set("itemRefs.$.type", item.getType()),
                UserCollection.class);

        return writeResult.getN();
    }

    private List<Item> findAllNotIn(Set<String> userItemsNames) {
        return mongoOperations.find(
                new Query(Criteria.where("name").not().in(userItemsNames)),
                Item.class);
    }
}
