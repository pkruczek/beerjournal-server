package com.beerjournal.breweriana.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class EventsService {

    private final EventQueue eventQueue;

    List<EventDto> getLatestEvents(int count) {
        return eventQueue.getLatestEvents(count);
    }

}
