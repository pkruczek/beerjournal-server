package com.beerjournal.breweriana.event;

import com.beerjournal.breweriana.event.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class EventService {

    private final EventRepository eventRepository;

    List<EventDto> getLatestEvents(int page, int count) {
        return eventRepository.findAll(page, count)
                .stream()
                .map(EventDto::of)
                .collect(Collectors.toList());
    }

}
