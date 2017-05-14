package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
import com.beerjournal.breweriana.event.DataType;
import com.beerjournal.breweriana.user.persistence.User;
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
        saveAsEvent(user, Action.CREATE);
    }

    private void saveAsEvent(User user, Action action) {
        Event event = Event.of(action, user, DataType.USER);
        eventRepository.save(event);
    }

}
