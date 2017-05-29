package com.beerjournal.breweriana.utils

import com.beerjournal.breweriana.collection.persistence.ItemRef
import com.beerjournal.breweriana.item.persistence.Attribute
import com.beerjournal.breweriana.item.persistence.Item
import com.beerjournal.breweriana.user.persistence.User
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.bson.types.ObjectId

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class TestUtils {

    static def someUser() {
        User.builder()
                .firstName("Janusz")
                .lastName("Nowak")
                .email("nowak@piwo.pl")
                .password("alamakota123")
                .build()
    }

    static def someItem() {
        someItem(new ObjectId())
    }

    static def someItem(ownerId) {
        Item.builder()
                .name("Butelka Ciechan")
                .brewery("Ciechan")
                .country("Polska")
                .type("bottle")
                .style("zwykłe")
                .attributes([Attribute.of("volume", "0.5")] as Set)
                .ownerId(ownerId)
                .build()
    }

    static def someItems(ownerId, startNameLetter, startTypeLetter) {
        LinkedList someItems = []
        for (int i = 0; i < 100; i++) {
            def item = Item.builder()
                    .name(startNameLetter + i)
                    .type(startTypeLetter + i)
                    .style("style")
                    .brewery("brewery")
                    .ownerId(ownerId)
                    .build()
            someItems.add(item)
        }
        return someItems
    }

    static def someObjectId() {
        new ObjectId()
    }

    static def someUsers() {
        [
                User.builder().firstName("Janusz").lastName("Nowak").email("janusz@wp.pl").build(),
                User.builder().firstName("Grazyna").lastName("Nowak").email("grazyna@onet.pl").build(),
                User.builder().firstName("Sebastian").lastName("Kowalski").email("seba@gmail.com").build()
        ] as Set
    }

    static def simpleItemRef(itemId) {
        ItemRef.builder().itemId(itemId).build()
    }

    static def simpleItemRef() {
        simpleItemRef(new ObjectId())
    }

    static void equalsOptionalValue(optional, value) {
        assert optional.isPresent()
        assert optional.get() == value
    }

}
