package com.beerjournal.breweriana.event;

import com.beerjournal.breweriana.event.persistence.Event;
import com.beerjournal.breweriana.item.ItemDto;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.user.UserDto;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static com.beerjournal.infrastructure.error.ErrorInfo.INCORRECT_EVENT_CONTENT_TYPE;
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
                .content(convertToProperDto(event))
                .build();
    }

    private static Object convertToProperDto(Event event) {
        ContentType type = ContentType.valueOf(event.getContentType());
        switch (type) {
            case USER:
                return UserDto.of((User) event.getContent());
            case ITEM:
                return ItemDto.of((Item) event.getContent());
            default:
                throw new BeerJournalException(INCORRECT_EVENT_CONTENT_TYPE);
        }
    }

}

