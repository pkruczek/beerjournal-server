package com.beerjournal.breweriana.persistence

import com.beerjournal.breweriana.persistence.category.Category
import com.beerjournal.breweriana.utils.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class CategoryRepositoryTest extends Specification {

    @Autowired
    CategoryRepository categoryRepository

    @Autowired
    CategoryCrudRepository categoryCrudRepository

    @Autowired
    MongoTemplate mongoTemplate

    def someCategories() {
        [
                Category.of("butelka"),
                Category.of("kapsel"),
                Category.of("etykieta")
        ] as Set
    }

    def setup() {
        mongoTemplate.db.dropDatabase()
    }

    def "should find all categories"() {
        setup:
        categoryCrudRepository.save(someCategories())

        expect:
        categoryRepository.findAll() == someCategories()
    }

    def "should return empty set if there is not any category"() {
        expect:
        categoryRepository.findAll() == [] as Set
    }

    def "should find category by name"() {
        setup:
        categoryCrudRepository.save(someCategories())

        expect:
        def maybeCategory = categoryRepository.findByName("butelka")
        TestUtils.equalsOptionalValue(maybeCategory, Category.of("butelka"))
    }

    def "should return empty optional when there is no category with given id"() {
        expect:
        def maybeCategory = categoryRepository.findByName("fakeName")
        !maybeCategory.isPresent()
    }

}
