package com.beerjournal.breweriana.item.persistence

import com.beerjournal.breweriana.category.persistence.Category
import com.beerjournal.breweriana.category.persistence.CategoryCrudRepository
import com.beerjournal.breweriana.collection.persistence.UserCollection
import com.beerjournal.breweriana.collection.persistence.UserCollectionCrudRepository
import com.beerjournal.breweriana.collection.persistence.UserCollectionRepository
import com.beerjournal.breweriana.user.persistence.UserCrudRepository
import com.beerjournal.breweriana.utils.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class ItemRepositoryTest extends Specification {

    @Autowired
    ItemRepository itemRepository

    @Autowired
    ItemCrudRepository itemCrudRepository

    @Autowired
    CategoryCrudRepository categoryRepository

    @Autowired
    UserCollectionCrudRepository userCollectionCrudRepository

    @Autowired
    UserCrudRepository userCrudRepository

    @Autowired
    MongoTemplate mongoTemplate

    def savedUser
    def someUserCollection

    def someItem = TestUtils.someItem()

    def setup() {
        savedUser = userCrudRepository.save(TestUtils.someUser())
        someUserCollection = UserCollection.builder()
                .ownerId(savedUser.id)
                .build()
    }

    def cleanup() {
        mongoTemplate.db.dropDatabase()
    }

    def "should save an item"() {
        when:
        itemRepository.save(someItem)

        then:
        def maybeItem = itemCrudRepository.findOneByName(someItem.name)
        TestUtils.equalsOptionalValue(maybeItem, someItem)
    }

    def "should add category if not exists (actually, no - delegated to listeners)"() {
        when:
        itemRepository.save(someItem)

        then:
        def maybeCategory = categoryRepository.findOneByName(someItem.category)
        TestUtils.equalsOptionalValue(maybeCategory, Category.of(someItem.category))
    }

    def "should add an item to collection (actually, no - delegated to listeners)"() {
        given:
        def savedCollection = userCollectionCrudRepository.save(someUserCollection)
        def newItem = TestUtils.someItem(savedUser.id)

        when:
        itemRepository.save(newItem)

        then:
        def maybeItem = itemCrudRepository.findOneByName(newItem.name)
        maybeItem.isPresent()

        def maybeCollection = userCollectionCrudRepository.findOneById(savedCollection.id)
        maybeCollection.isPresent()
        maybeCollection.get().itemRefs.contains(maybeItem.get().asItemRef())
    }


}