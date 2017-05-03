package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.ITEM_NOT_FOUND;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    ItemDto getItemDetails(String id) {
        Item item = itemRepository.findOneById(ServiceUtils.stringToObjectId(id))
                .orElseThrow(() -> new BeerJournalException(ITEM_NOT_FOUND));

        return ItemDto.toDto(item);
    }

    ItemDto addItem(String ownerId, ItemDto itemDto) {
        userRepository.findOneById(ServiceUtils.stringToObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));

        Item item = ItemDto.fromDto(itemDto, ownerId);
        itemRepository.save(item);
        return itemDto;
    }

    ItemDto deleteItem(String ownerId, String itemId) {
        userRepository.findOneById(ServiceUtils.stringToObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));

        Item deletedItem = itemRepository.delete(itemId);
        return ItemDto.toDto(deletedItem);
    }
}
