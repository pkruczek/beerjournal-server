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
            .attributes([Attribute.of("volume", 0.5 as Double)] as Set)
            .build()

    def setup() {
        savedUser = userRepository.save(TestUtils.someUser())
        someUserCollection = UserCollection.builder()
                .ownerId(savedUser.id)
                .build()
        savedUsersItem = TestUtils.someItem(savedUser.id)
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
        def missingItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id);

        then:
        missingItems == [someOtherItem.asItemRef()] as Set
    }

    def "shouldn't return owned item"() {
        given:
        crudRepository.save(someUserCollection)
        itemRepository.save(savedUsersItem)

        when:
        def missingItems = userCollectionRepository.findAllNotInUserCollection(savedUser.id);

        then:
        !missingItems.contains(someOtherItem)
    }

}
