package com.beerjournal.breweriana.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class EventService {

    private final EventQueue eventQueue;

    List<EventDto> getLatestEvents(int count) {
        return eventQueue.getLatestEvents(count);
    }

}
