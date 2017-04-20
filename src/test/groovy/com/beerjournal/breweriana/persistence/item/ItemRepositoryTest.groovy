package com.beerjournal.breweriana.persistence.item

import com.beerjournal.breweriana.persistence.category.Category
import com.beerjournal.breweriana.persistence.CategoryCrudRepository
import com.beerjournal.breweriana.persistence.ItemCrudRepository
import com.beerjournal.breweriana.persistence.ItemRepository
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
    MongoTemplate mongoTemplate

    def setup() {
        mongoTemplate.db.dropDatabase()
    }

    def someItem = TestUtils.someItem()

    def "should save an item"() {
        when:
        itemRepository.save(someItem)

        then:
        def maybeItem = itemCrudRepository.findOneByName(someItem.name)
        TestUtils.equalsOptionalValue(maybeItem, someItem)
    }

    def "should add category if not exists"() {
        when:
        itemRepository.save(someItem)

        then:
        def maybeCategory = categoryRepository.findOneByName(someItem.category)
        TestUtils.equalsOptionalValue(maybeCategory, Category.of(someItem.category))
    }
}
