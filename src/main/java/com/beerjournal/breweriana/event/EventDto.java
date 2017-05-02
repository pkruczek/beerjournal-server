package com.beerjournal.breweriana.events;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.beerjournal.breweriana.events.EventType.ITEM_;
import static com.beerjournal.breweriana.events.EventType.USER_;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class EventDto {

    private final String authorId;
    private final String itemId;
    private final String name;
    private final String type;
    private final String action;
    private final String date;
    //// TODO: 2017-04-29 add image

    public static EventDto of(Item item, Action action){
        return EventDto.builder()
                .authorId(item.getOwnerId().toHexString())
                .itemId(item.getId().toHexString())
                .name(item.getName())
                .type(item.getType())
                .action(ITEM_ + action.toString())
                .date(LocalDateTime.now().toString())
                .build();
    }

    public static EventDto of(User user, Action action){
        return EventDto.builder()
                .authorId(user.getId().toHexString())
                .name(user.getFirstName() + " " + user.getLastName())
                .type("Users")
                .action(USER_ + action.toString())
                .date(LocalDateTime.now().toString())
                .build();
    }
}

enum EventType {
    USER_,
    ITEM_
}

