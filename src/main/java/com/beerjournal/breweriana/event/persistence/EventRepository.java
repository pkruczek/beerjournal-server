package com.beerjournal.breweriana.event.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final EventCrudRepository crudRepository;

    Event save(Event event) {
        return crudRepository.save(event);
    }

    public List<Event> findAll(int page, int size) {
        Page<Event> eventPage = crudRepository.findAll(
                new PageRequest(
                        page,
                        size,
                        new Sort(DESC, "Id"))
        );
        return eventPage.getContent();
    }

}
