package com.beerjournal.breweriana.persistence;

import com.beerjournal.breweriana.item.ItemDto;
import com.beerjournal.breweriana.persistence.collection.ItemRef;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import com.mongodb.WriteResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepository {

    private final UserCollectionCrudRepository crudRepository;
    private final MongoOperations mongoOperations;
    private final ItemRepository itemRepository;

    public int addNewItem(ObjectId ownerId, Item item) {
        Item savedDetails = itemRepository.save(item);
        ItemRef itemRef = savedDetails.asItemRef();

        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(ownerId)),
                new Update().push("itemRefs", itemRef),
                UserCollection.class);

        return writeResult.getN();
    }

    public ItemDto deleteItem(ObjectId ownerId, String itemId) {
        Item item = itemRepository.findOneById(ServiceUtils.stringToObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
        ItemRef itemRef = item.asItemRef();

        mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(ownerId)),
                new Update().pull("itemRefs", itemRef),
                UserCollection.class);

        return ItemDto.toDto(item);
    }

    public Optional<UserCollection> findOneByOwnerId(ObjectId ownerId) {
        return crudRepository.findOneByOwnerId(ownerId);
    }
}
