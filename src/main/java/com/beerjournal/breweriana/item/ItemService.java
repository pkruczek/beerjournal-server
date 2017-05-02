package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
class ItemService {

    private final ItemRepository itemRepository;

    ItemDto getItemDetails(String id) {
        Item item = itemRepository.findOneById(ServiceUtils.stringToObjectId(id))
                .orElseThrow(() -> new BeerJournalException(ITEM_NOT_FOUND));

        return ItemDto.toDto(item);
    }

    ItemDto addItem(String ownerId, ItemDto itemDto) {
        Item item = ItemDto.fromDto(itemDto, ownerId);
        Item savedItem = itemRepository.save(item);
        return ItemDto.toDto(savedItem);
    }

}
