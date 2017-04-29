package com.beerjournal.breweriana.events;

import com.beerjournal.breweriana.item.ItemDto;
import com.beerjournal.breweriana.user.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class EventDto {

    private final String authorId;
    private final String itemId;
    private final String name;
    private final String category;
    private final String action;
    private final String date;
    //// TODO: 2017-04-29 add image

    public static EventDto of(ItemDto item){
        return EventDto.builder()
                .authorId(item.getOwnerId())
                .itemId(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .action(Actions.NEW_ITEM.toString())
                .date(LocalDateTime.now().toString())
                .build();
    }

    public static EventDto of(UserDto user){
        return EventDto.builder()
                .authorId(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .category("Users")
                .action(Actions.NEW_USER.toString())
                .date(LocalDateTime.now().toString())
                .build();
    }
}

enum Actions{
    NEW_ITEM,
    NEW_USER
}
