package com.beerjournal.breweriana.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
class EventController {

    private final EventService eventService;

    @GetMapping
    ResponseEntity<Page<EventDto>> getEventsFeed(
            @RequestParam(value = "count", defaultValue = "10") int count,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(eventService.getLatestEvents(page, count), HttpStatus.OK);
    }

}
