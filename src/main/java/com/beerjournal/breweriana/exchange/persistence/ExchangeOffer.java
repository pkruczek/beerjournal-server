package com.beerjournal.breweriana.exchange.persistence;

import com.beerjournal.breweriana.collection.persistence.ItemRef;
import lombok.*;
import lombok.experimental.Wither;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@Wither
@EqualsAndHashCode(exclude = {"id", "created"})
@RequiredArgsConstructor(access = PRIVATE)
public final class ExchangeOffer {

    @Id
    private final ObjectId id;
    private final ObjectId offerorId;
    private final ObjectId ownerId;
    private final Set<ItemRef> desiredItems;
    private final Set<ItemRef> offeredItems;
    private final ExchangeState state;
    private final LocalDateTime created;

    public Set<ItemRef> getOfferedItems() {
        return Collections.unmodifiableSet(offeredItems);
    }

    public Set<ItemRef> getDesiredItems() {
        return Collections.unmodifiableSet(desiredItems);
    }

    @Builder
    public static ExchangeOffer of(ObjectId offerorId, ObjectId ownerId, @Singular  Set<ItemRef> desiredItems,
                                   @Singular Set<ItemRef> offeredItems, ExchangeState state) {
        return new ExchangeOffer(null, offerorId, ownerId, desiredItems, offeredItems, state, null);
    }

}
