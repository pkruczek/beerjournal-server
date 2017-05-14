package com.beerjournal.breweriana.collection.persistence

import com.beerjournal.breweriana.item.persistence.Attribute
import com.beerjournal.breweriana.item.persistence.Item
import com.beerjournal.breweriana.item.persistence.ItemRepository
import com.beerjournal.breweriana.user.persistence.UserCrudRepository
import com.beerjournal.breweriana.utils.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class UserCollectionRepositoryTest extends Specification {

    @Autowired
    UserCrudRepository userRepository

    @Autowired
    UserCollectionRepository userCollectionRepository

    @Autowired
    UserCollectionCrudRepository crudRepository

    @Autowired
    ItemRepository itemRepository

    @Autowired
    MongoTemplate mongoTemplate

    def savedUser
    def someUserCollection

    def savedUsersItem
    def someOtherItem = Item.builder()
            .name("Butelka Tyskie")
            .brewery("tyskie")
            .country("Polska")
            .type("bottle")
            .style("zwykłe")
            .attributes([Attribute.of("volume", "0.5")] as Set)
            .ownerId(TestUtils.someObjectId())
            .build()

    LinkedList someOtherItems = []
    LinkedList savedUserItems = []

    def setup() {
        savedUser = userRepository.save(TestUtils.someUser())
        someUserCollection = UserCollection.builder()
                .ownerId(savedUser.id)
                .build()
        savedUsersItem = TestUtils.someItem(savedUser.id)
        someOtherItems = TestUtils.someItems(TestUtils.someObjectId(), "s", "t")
        savedUserItems = TestUtils.someItems(TestUtils.someObjectId(), "g", "h")
    }

    def cleanup() {
        mongoTemplate.db.dropDatabase()
    }

    def "should return not owned items"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)
        itemRepository.save(someOtherItem)

        when:
        def missingItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id, 0, 10, "", "")

        then:
        missingItems.getContent() == [someOtherItem.asItemRef()] as List
    }

    def "shouldn't return owned item"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)

        when:
        def missingItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id, 0, 10, "", "")

        then:
        !missingItems.getContent().contains(someOtherItem)
    }

    def "should return correct page details for not owned items that name starts with 's1'"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)
        for (item in someOtherItems)
            itemRepository.save(item as Item)

        when:
        def missingItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id, 0, 10, "name", "s1")

        then:
        missingItems.getTotalElements() == 11 && missingItems.getTotalPages() == 2 && missingItems.getNumberOfElements() == 10

    }

    def "should return correct page details for not owned items that type starts with 't1'"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)
        for (item in someOtherItems)
            itemRepository.save(item as Item)

        when:
        def missingItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id, 1, 10, "type", "t1")

        then:
        missingItems.getTotalElements() == 11 && missingItems.getTotalPages() == 2 && missingItems.getNumberOfElements() == 1

    }

    def "should return correct page details for owned items that name starts with 'g1'"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)
        for (item in savedUserItems)
            itemRepository.save(item as Item)

        when:
        def ownedItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id, 0, 10, "name", "g1")

        then:
        ownedItems.getTotalElements() == 11 && ownedItems.getTotalPages() == 2 && ownedItems.getNumberOfElements() == 10
    }

    def "should return correct page details for owned items that type starts with 'h1'"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)
        for (item in savedUserItems)
            itemRepository.save(item as Item)

        when:
        def ownedItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id, 1, 10, "type", "h1")

        then:
        ownedItems.getTotalElements() == 11 && ownedItems.getTotalPages() == 2 && ownedItems.getNumberOfElements() == 1
    }

}
