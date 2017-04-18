package com.beerjournal.breweriana.persistence.collection

import com.beerjournal.breweriana.persistence.item.ItemCrudRepository
import com.beerjournal.breweriana.persistence.user.UserRepository
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
    UserRepository userRepository

    @Autowired
    UserCollectionRepository userCollectionRepository

    @Autowired
    UserCollectionCrudRepository crudRepository

    @Autowired
    ItemCrudRepository itemCrudRepository

    @Autowired
    MongoTemplate mongoTemplate

    def savedUser
    def someUserCollection

    def setup() {
        savedUser = userRepository.save(TestUtils.someUser())
        someUserCollection = UserCollection.builder()
                .ownerId(savedUser.id)
                .build()
        mongoTemplate.db.dropDatabase()
    }

    def "should save a collection"() {
        when:
        userCollectionRepository.save(someUserCollection)

        then:
        def maybeUserCollection = crudRepository.findOneByOwnerId(savedUser.id)
        TestUtils.equalsOptionalValue(maybeUserCollection, someUserCollection)
    }

    def "should add an item to collection"() {
        given:
        def savedCollection = crudRepository.save(someUserCollection)
        def newItem = TestUtils.someItem(savedUser.id)

        when:
        userCollectionRepository.addNewItem(savedUser.id, newItem)

        then:
        def maybeItem = itemCrudRepository.findOneByName(newItem.name)
        maybeItem.isPresent()

        def maybeCollection = crudRepository.findOneById(savedCollection.id)
        maybeCollection.isPresent()
        maybeCollection.get().itemRefs.contains(maybeItem.get().asItemRef())
    }
}
