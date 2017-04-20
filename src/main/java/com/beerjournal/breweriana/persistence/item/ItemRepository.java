package com.beerjournal.breweriana.persistence.item;

import com.beerjournal.breweriana.persistence.category.Category;
import com.beerjournal.breweriana.persistence.category.CategoryRepository;
import com.beerjournal.breweriana.persistence.collection.ItemRef;
import com.beerjournal.breweriana.persistence.collection.UserCollection;
import com.beerjournal.breweriana.persistence.collection.UserCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final ItemCrudRepository crudRepository;
    private final CategoryRepository categoryRepository;
    private final UserCollectionRepository userCollectionRepository;

    public Item save(Item item) {
        ensureCategory(item.getCategory());
        return crudRepository.save(item);
    }

    private void ensureCategory(String category) {
        Optional<Category> maybeCategory = categoryRepository.findOneByName(category);
        if (!maybeCategory.isPresent()) {
            categoryRepository.save(Category.of(category));
        }
    }

    public Set<Item> findAllNotInUserCollection(ObjectId ownerId) {
        UserCollection userCollection = userCollectionRepository.findByOwnerId(ownerId)
                .orElseThrow(RuntimeException::new); //TODO Better error handling
        Set<String> userItemsNames = userCollection.getItemRefs().stream()
                .map(ItemRef::getName)
                .collect(Collectors.toSet());
        return crudRepository.findByNameNotIn(userItemsNames);
    }

}
