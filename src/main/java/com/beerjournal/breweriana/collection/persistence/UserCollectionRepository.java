package com.beerjournal.breweriana.collection.persistence;

import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.google.common.base.Strings;
import com.mongodb.WriteResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.beerjournal.infrastructure.error.ErrorInfo.USER_COLLECTION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepository {

    private final UserCollectionCrudRepository crudRepository;
    private final MongoOperations mongoOperations;

    public Optional<UserCollection> findOneByOwnerId(ObjectId ownerId) {
        return crudRepository.findOneByOwnerId(ownerId);
    }

    public Page<ItemRef> findAllInUserCollection(ObjectId ownerId, int page, int count, String filterVariableName, String filterVariableValue) {
        UserCollection userCollection = crudRepository.findOneByOwnerId(ownerId)
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));
        List<ItemRef> list = new ArrayList<>(userCollection.getItemRefs());

        Stream<ItemRef> listStream = Strings.isNullOrEmpty(filterVariableValue) ?
                list.stream() :
                list.stream().filter(v -> filterVariableName.equals("name") ?
                        v.getName().startsWith(filterVariableValue) : v.getType().startsWith(filterVariableValue));

        return new PageImpl<>(
                listStream
                        .skip(page * count)
                        .limit(count)
                        .collect(Collectors.toList()),
                new PageRequest(page, count),
                list.size());
    }

    public Page<ItemRef> findAllNotInUserCollection(ObjectId ownerId, int page, int count, String filterVariableName, String filterVariableValue) {
        UserCollection userCollection = crudRepository.findOneByOwnerId(ownerId)
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        Set<String> userItemsNames = userCollection.getItemRefs().stream()
                .map(ItemRef::getName)
                .collect(Collectors.toSet());

        return findAllNotIn(userItemsNames, new PageRequest(page, count), filterVariableName, filterVariableValue)
                .map(Item::asItemRef);
    }

    UserCollection deleteOneByOwnerId(ObjectId ownerId) {
        findOneByOwnerId(ownerId)
                .orElseThrow(() -> new BeerJournalException(USER_COLLECTION_NOT_FOUND));

        return mongoOperations.findAndRemove(
                new Query(Criteria.where("ownerId").is(ownerId)),
                UserCollection.class);
    }

    UserCollection save(UserCollection userCollection) {
        return crudRepository.save(userCollection);
    }

    int addNewItem(Item item) {
        ItemRef itemRef = item.asItemRef();

        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(item.getOwnerId())),
                new Update().push("itemRefs", itemRef),
                UserCollection.class);

        return writeResult.getN();
    }

    int deleteItem(Item item) {
        ItemRef itemRef = item.asItemRef();

        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(Criteria.where("ownerId").is(item.getOwnerId())),
                new Update().pull("itemRefs", itemRef),
                UserCollection.class);

        return writeResult.getN();
    }

    int updateItem(Item item) {
        WriteResult writeResult = mongoOperations.updateFirst(
                new Query(new Criteria().andOperator(
                        Criteria.where("ownerId").is(item.getOwnerId()),
                        Criteria.where("itemRefs").elemMatch(Criteria.where("itemId").is(item.getId()))
                )),
                new Update().set("itemRefs.$.name", item.getName())
                        .set("itemRefs.$.type", item.getType()),
                UserCollection.class);

        return writeResult.getN();
    }

    private Page<Item> findAllNotIn(Set<String> userItemsNames, PageRequest pageRequest, String filterVariableName, String filterVariableValue) {
        Criteria criteria = Criteria.where("name").not().in(userItemsNames);
        if (!Strings.isNullOrEmpty(filterVariableValue))
            criteria = criteria.andOperator(Criteria.where(filterVariableName).regex(Pattern.compile("^" + filterVariableValue)));

        Query query = new Query(criteria);
        long total = mongoOperations.count(query, Item.class);
        List<Item> items = mongoOperations.find(query.with(pageRequest), Item.class);

        return new PageImpl<>(items, pageRequest, total);
    }
}
