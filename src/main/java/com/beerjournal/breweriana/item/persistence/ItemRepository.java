package com.beerjournal.breweriana.item.persistence;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final ItemCrudRepository crudRepository;
    private final Set<ItemUpdateListener> itemUpdateListeners;

    public Optional<Item> findOneById(ObjectId id) {
        return crudRepository.findOneById(id);
    }

    public Item save(Item item) {
        Item savedItem = crudRepository.save(item);
        itemUpdateListeners.forEach(listener -> listener.onInsert(item));
        return savedItem;
    }
}
