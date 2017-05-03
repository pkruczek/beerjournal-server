package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
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
        Event event = Event.of(Action.CREATE, item);
        eventRepository.save(event);
    }

    @Override
    public void onUpdate(Item item) {
        Event event = Event.of(Action.UPDATE, item);
        eventRepository.save(event);
    }

    @Override
    public void onDelete(Item item) {
        Event event = Event.of(Action.DELETE, item);
        eventRepository.save(event);
    }

}
