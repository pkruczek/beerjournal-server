package com.beerjournal.breweriana.exchange.persistence;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ExchangeRepository {

    private final ExchangeCrudRepository crudRepository;
    private final MongoOperations mongoOperations;

    public Optional<ExchangeOffer> findOneById(ObjectId id) {
        return crudRepository.findOneById(id);
    }

    public ExchangeOffer save(ExchangeOffer exchangeOffer) {
        return crudRepository.save(exchangeOffer);
    }

    public Stream<ExchangeOffer> findAllByOwnerId(ObjectId ownerId) {
        return crudRepository.findAllByOwnerId(ownerId);
    }

    public Stream<ExchangeOffer> findAllByOfferorId(ObjectId ownerId) {
        return crudRepository.findAllByOfferorId(ownerId);
    }

    public Stream<ExchangeOffer> findAllByOfferorIdAndItemId(ObjectId offerorId, ObjectId itemId) {
        List<ExchangeOffer> exchangeOffers = mongoOperations.find(
                new Query(
                        new Criteria().andOperator(
                                Criteria.where("offerorId").is(offerorId),
                                Criteria.where("desiredItems").elemMatch(Criteria.where("itemId").is(itemId)))),
                ExchangeOffer.class);

        return exchangeOffers.stream();
    }

    public Stream<ExchangeOffer> findMatchingExchange(ObjectId offerorId, ObjectId ownerId, Set<ObjectId> desiredItemIds,
                                                      Set<ObjectId> offeredItemIds) {
        return crudRepository.findAllByOfferorIdAndOwnerId(offerorId, ownerId)
                .filter(e -> itemRefIdsEquals(e.getDesiredItems(), desiredItemIds))
                .filter(e -> itemRefIdsEquals(e.getOfferedItems(), offeredItemIds));
    }

    private boolean itemRefIdsEquals(Set<ItemRef> itemRefs, Set<ObjectId> ids) {
        return toIds(itemRefs)
                .equals(ids);
    }

    private Set<ObjectId> toIds(Set<ItemRef> itemRefs) {
        return itemRefs.stream()
                .map(ItemRef::getItemId)
                .collect(Collectors.toSet());
    }

}
