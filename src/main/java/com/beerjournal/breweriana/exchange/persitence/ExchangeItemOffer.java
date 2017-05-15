package com.beerjournal.breweriana.exchange.persitence;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import lombok.*;
import lombok.experimental.Wither;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@Wither
@EqualsAndHashCode(exclude = {"id"})
@RequiredArgsConstructor(access = PRIVATE)
public class ExchangeItemOffer {

    @Id
    private final ObjectId id;
    private final ObjectId offerorId;
    private final ObjectId ownerId;
    private final ItemRef desiredItem;
    private final Set<ItemRef> offeredItems;
    private final boolean acceptedByOwner;

    public Set<ItemRef> getOfferedItems() {
        return Collections.unmodifiableSet(offeredItems);
    }

    @Builder
    public static ExchangeItemOffer of(ObjectId offerorId, ObjectId ownerId, ItemRef desiredItem,
                                       @Singular Set<ItemRef> offeredItems, boolean acceptedByOwner) {
        return new ExchangeItemOffer(null, offerorId, ownerId, desiredItem, offeredItems, acceptedByOwner);
    }

}
