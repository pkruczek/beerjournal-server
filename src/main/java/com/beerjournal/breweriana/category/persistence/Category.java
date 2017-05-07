package com.beerjournal.breweriana.category.persistence;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document
@Data
@Wither
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor(access = PRIVATE)
public final class Category {

    @Id
    private final ObjectId id;
    @Indexed(unique = true)
    private final String name;
    private final Set<Object> values;

    public Category withNewValue(Object value) {
        return withValues(Sets.union(this.values, ImmutableSet.of(value)));
    }

    public static Category of(String name, Set<Object> values) {
        return new Category(null, name, values);
    }

}
