package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.collection.persistence.UserCollection;
import com.beerjournal.breweriana.collection.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;
import static com.beerjournal.infrastructure.error.ErrorInfo.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CollectionService {

    private final UserCollectionRepository userCollectionRepository;
    private final UserRepository userRepository;

    UserCollectionDto getCollectionByOwnerId(String ownerId) {
        UserCollection userCollection = getUserCollectionOrThrow(ownerId);

        return UserCollectionDto.toDto(userCollection);
    }

    Set<ItemRefDto> getAllItemRefsInUserCollection(String userId) {
        UserCollection userCollection = getUserCollectionOrThrow(userId);

        return userCollection.getItemRefs()
                .stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());
    }

    Set<ItemRefDto> getAllNotInUserCollection(String userId) {
        return userCollectionRepository.findAllNotInUserCollection(ServiceUtils.stringToObjectId(userId))
                .stream()
                .map(ItemRefDto::toDto)
                .collect(Collectors.toSet());
    }

    private UserCollection getUserCollectionOrThrow(String userId) {
        return userCollectionRepository
                .findOneByOwnerId(ServiceUtils.stringToObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));
    }

}
