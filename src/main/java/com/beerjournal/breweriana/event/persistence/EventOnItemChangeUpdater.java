package com.beerjournal.breweriana.events.persistence;

import com.beerjournal.breweriana.events.Action;
import com.beerjournal.breweriana.events.EventDto;
import com.beerjournal.breweriana.events.EventQueue;
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
        EventDto newItem = EventDto.of(item, Action.CREATED);
        eventQueue.addEvent(newItem);
    }

    @Override
    public void onUpdate(Item item) {

    }

    @Override
    public void onDelete(Item item) {

    }

}
