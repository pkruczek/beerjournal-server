package com.beerjournal.breweriana.collection;

import com.beerjournal.breweriana.collection.persistence.UserCollection;
import com.beerjournal.breweriana.collection.persistence.UserCollectionRepository;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
class CollectionService {

    private final UserCollectionRepository userCollectionRepository;

    UserCollectionDto getCollectionByOwnerId(String ownerId) {
        UserCollection userCollection = getUserCollectionOrThrow(ownerId);

        return UserCollectionDto.of(userCollection);
    }

    Page<ItemRefDto> getAllItemRefsInUserCollection(String userId,
                                                    int page,
                                                    int count,
                                                    Map<String, String> filterRequestParams,
                                                    String sortBy,
                                                    String sortType) {
        return userCollectionRepository
                .findAllInUserCollection(Converters.toObjectId(userId), page, count, filterRequestParams, sortBy, sortType)
                .map(ItemRefDto::toDto);
    }

    Page<ItemRefDto> getAllNotInUserCollection(String userId,
                                               int page,
                                               int count,
                                               Map<String, String> filterRequestParams,
                                               String sortBy,
                                               String sortType) {
        return userCollectionRepository
                .findAllNotInUserCollection(Converters.toObjectId(userId), page, count, filterRequestParams, sortBy, sortType)
                .map(ItemRefDto::toDto);
    }

    private UserCollection getUserCollectionOrThrow(String userId) {
        return userCollectionRepository
                .findOneByOwnerId(Converters.toObjectId(userId))
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));
    }
}
