package com.beerjournal.breweriana.event.persistence;

import com.beerjournal.breweriana.event.Action;
import com.beerjournal.breweriana.event.EventDto;
import com.beerjournal.breweriana.event.EventQueue;
import com.beerjournal.breweriana.user.User;
import com.beerjournal.breweriana.utils.UpdateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor(access = PRIVATE)
public class EventOnUserChangeUpdater implements UpdateListener<User> {

    private final EventQueue eventQueue;

    @Override
    public void onInsert(User user) {
        EventDto userCreated = EventDto.of(user, Action.CREATED);
        eventQueue.addEvent(userCreated);
    }

}
