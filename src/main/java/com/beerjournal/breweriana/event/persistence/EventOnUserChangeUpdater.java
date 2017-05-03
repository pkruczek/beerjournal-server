package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
import com.beerjournal.breweriana.user.User;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor(access = PRIVATE)
public class EventOnUserChangeUpdater implements UpdateListener<User> {

    private final EventRepository eventRepository;

    @Override
    public void onInsert(User user) {
        Event event = Event.of(Action.CREATE, user);
        eventRepository.save(event);
    }

}
