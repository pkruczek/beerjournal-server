package com.beerjournal.breweriana.persistence.collection;

import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.persistence.item.ItemRepository;
import com.mongodb.WriteResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepository {

    private final UserCollectionCrudRepository crudRepository;
    private final MongoOperations mongoOperations;
    private final ItemRepository itemRepository;

    public UserCollection save(UserCollection userCollection) {
        return crudRepository.save(userCollection);
    }

    public int addNewItem(ObjectId ownerId, Item item) {
        Item savedDetails = itemRepository.save(item);
        ItemRef itemRef = savedDetails.asItemRef();

        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(ownerId)),
                new Update().push("itemRefs", itemRef),
                UserCollection.class);

        return writeResult.getN();
    }
}
