package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.item.ItemDto;
import com.beerjournal.breweriana.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.persistence.UserRepository;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.item.Item;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CollectionService {

    private final UserCollectionRepository userCollectionRepository;
    private final UserRepository userRepository;

    UserCollectionDto getCollectionByOwnerId(String ownerId) {
        UserCollection userCollection = userCollectionRepository.findOneByOwnerId(ServiceUtils.stringToObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        return UserCollectionDto.toDto(userCollection);
    }

    ItemDto addItem(String ownerId, ItemDto itemDto) {
        userRepository.findOneById(ServiceUtils.stringToObjectId(ownerId))
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));

        Item item = ItemDto.fromDto(itemDto, ownerId);
        userCollectionRepository.addNewItem(ServiceUtils.stringToObjectId(ownerId), item);
        return itemDto;
    }

}
