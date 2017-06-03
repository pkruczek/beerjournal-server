package com.beerjournal.breweriana.event;

import com.beerjournal.breweriana.event.persistence.Event;
import com.beerjournal.breweriana.item.ItemDto;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.user.UserDto;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.beerjournal.infrastructure.error.ErrorInfo.INCORRECT_EVENT_DATA_TYPE;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
class EventDto {

    private final String action;
    private final String date;
    private final String dataType;
    private final Object data;
    private final LocalDateTime created;


    public static EventDto of(Event event) {
        return EventDto.builder()
                .action(event.getAction())
                .date(Converters.toInstant(event.getId()).toString())
                .dataType(event.getDataType())
                .data(convertToProperDto(event))
                .created(event.getCreated())
                .build();
    }

    private static Object convertToProperDto(Event event) {
        DataType type = DataType.valueOf(event.getDataType());
        switch (type) {
            case USER:
                return UserDto.of((User) event.getData());
            case ITEM:
                return ItemDto.of((Item) event.getData());
            default:
                throw new BeerJournalException(INCORRECT_EVENT_DATA_TYPE);
        }
    }

}

