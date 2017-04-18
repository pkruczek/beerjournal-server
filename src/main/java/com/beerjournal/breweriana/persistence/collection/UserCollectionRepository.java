package com.beerjournal.breweriana.persistence.collection;

import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.persistence.item.ItemRepository;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.mongodb.WriteResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepository {

    private final UserCollectionCrudRepository crudRepository;
    private final MongoTemplate mongoTemplate;
    private final ItemRepository itemRepository;

    public UserCollection save(UserCollection userCollection) {
        return crudRepository.save(userCollection);
    }

    public void addNewItem(ObjectId ownerId, Item item) {
        Item savedDetails = itemRepository.save(item);
        ItemRef itemRef = savedDetails.asItemRef();

        WriteResult writeResult = mongoTemplate.updateFirst(
                new Query(Criteria.where("ownerId").is(ownerId)),
                new Update().push("itemRefs", itemRef),
                UserCollection.class);

        if(writeResult.getN() != 1) {
            //FIXME add better error handling
            throw new RuntimeException("updated count != 1");
        }
    }
}
