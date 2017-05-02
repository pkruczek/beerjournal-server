package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
import com.beerjournal.breweriana.event.EventDto;
import com.beerjournal.breweriana.event.EventQueue;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor(access = PRIVATE)
public class EventOnItemChangeUpdater implements UpdateListener<Item> {

    private final EventQueue eventQueue;

    @Override
    public void onInsert(Item item) {
        EventDto itemCreated = EventDto.of(item, Action.CREATED);
        eventQueue.addEvent(itemCreated);
    }

    @Override
    public void onUpdate(Item item) {
        EventDto itemUpdated = EventDto.of(item, Action.UPDATED);
        eventQueue.addEvent(itemUpdated);
    }

    @Override
    public void onDelete(Item item) {
        EventDto itemDeleted = EventDto.of(item, Action.DELETED);
        eventQueue.addEvent(itemDeleted);
    }

}
