package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
import com.beerjournal.breweriana.event.DataType;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor(access = PRIVATE)
public class EventOnItemChangeUpdater implements UpdateListener<Item> {

    private final EventRepository eventRepository;

    @Override
    public void onInsert(Item item) {
        saveAsEvent(item, Action.CREATE);
    }

    @Override
    public void onUpdate(Item item) {
        saveAsEvent(item, Action.UPDATE);
    }

    @Override
    public void onDelete(Item item) {
        saveAsEvent(item, Action.DELETE);
    }

    private void saveAsEvent(Item item, Action action) {
        Event event = Event.of(action, item, DataType.ITEM);
        eventRepository.save(event);
    }

}
