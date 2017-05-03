package com.beerjournal.breweriana.event;

import com.beerjournal.breweriana.event.persistence.Event;
import com.beerjournal.breweriana.utils.ServiceUtils;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class EventDto {

    private final String action;
    private final String date;
    private final String contentType;
    private final Object content;

    public static EventDto of(Event event) {
        return EventDto.builder()
                .action(event.getAction())
                .date(ServiceUtils.objectIdToDateInstant(event.getId()).toString())
                .contentType(event.getContentType())
                .content(event.getContent())
                .build();
    }

}

