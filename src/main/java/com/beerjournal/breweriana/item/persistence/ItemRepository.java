package com.beerjournal.breweriana.item.persistence;

import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.breweriana.utils.UpdateListener;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final ItemCrudRepository crudRepository;
    private final Set<UpdateListener<Item>> itemUpdateListeners;

    public Optional<Item> findOneById(ObjectId id) {
        return crudRepository.findOneById(id);
    }

    public Item save(Item item) {
        Item savedItem = crudRepository.save(item);
        itemUpdateListeners.forEach(listener -> listener.onInsert(item));
        return savedItem;
    }

    public Item delete(String itemId) {
        Item itemToDelete = crudRepository.findOneById(ServiceUtils.stringToObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));

        itemUpdateListeners.forEach(listener -> listener.onDelete(itemToDelete));
        return itemToDelete;
    }
}
