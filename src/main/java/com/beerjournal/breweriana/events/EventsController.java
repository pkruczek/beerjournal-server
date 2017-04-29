package com.beerjournal.breweriana.events;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
class EventsController {

    private final EventsService eventsService;

    @GetMapping
    ResponseEntity<Collection<EventDto>> getEventsFeed(
            @RequestParam(value = "count", defaultValue = "50") int count) {
        return new ResponseEntity<>(eventsService.getLatestEvents(count), HttpStatus.OK);
    }

}
