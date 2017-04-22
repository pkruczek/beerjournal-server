package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.persistence.ItemRepository;
import com.beerjournal.breweriana.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.persistence.collection.ItemRef;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.ITEM_NOT_FOUND;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
class ItemService {

    private final ItemRepository itemRepository;
    private final UserCollectionRepository userCollectionRepository;

    Set<ItemDto> getAllNotInUserCollection(String userId) {
        return itemRepository.findAllNotInUserCollection(ServiceUtils.stringToObjectId(userId))
                .stream()
                .map(ItemDto::toDto)
                .collect(Collectors.toSet());
    }

    Set<ItemRefDto> getAllItemRefsInUserCollection(String userId) {
        UserCollection userCollection = userCollectionRepository
                .findByOwnerId(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        return userCollection.getItemRefs()
                .stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());
    }

    ItemDto getItemDetails(String id) {
        Item item = itemRepository.findOneById(id)
                .orElseThrow(() -> new BeerJournalException(ITEM_NOT_FOUND));

        return ItemDto.toDto(item);
    }

}
