package com.beerjournal.breweriana.event;

import com.beerjournal.breweriana.event.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class EventService {

    private final EventRepository eventRepository;

    Page<EventDto> getLatestEvents(int page, int count) {
        return eventRepository
                .findAll(page, count)
                .map(EventDto::of);
    }

}
