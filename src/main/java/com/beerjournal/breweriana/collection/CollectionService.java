package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.events.EventDto;
import com.beerjournal.breweriana.events.EventQueue;
import com.beerjournal.breweriana.item.ItemDto;
import com.beerjournal.breweriana.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CollectionService {

    private final UserCollectionRepository userCollectionRepository;
    private final EventQueue eventQueue;

    UserCollectionDto getCollectionByOwnerId(String ownerId) {
        UserCollection userCollection = userCollectionRepository.findOneByOwnerId(ServiceUtils.stringToObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        return UserCollectionDto.toDto(userCollection);
    }

    ItemDto addItem(String ownerId, ItemDto itemDto) {
        Item item = ItemDto.fromDto(itemDto, ownerId);
        Item savedItem = userCollectionRepository.addNewItem(ServiceUtils.stringToObjectId(ownerId), item);

        ItemDto savedItemDto = ItemDto.toDto(savedItem);
        eventQueue.addEvent(EventDto.of(savedItemDto));
        return savedItemDto;
    }

}
