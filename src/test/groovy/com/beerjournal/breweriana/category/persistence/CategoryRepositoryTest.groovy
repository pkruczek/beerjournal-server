package com.beerjournal.breweriana.category.persistence

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
                Category.of("type", [] as Set),
                Category.of("brewery", [] as Set),
                Category.of("country", [] as Set)
        ] as Set
    }

    def cleanup() {
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
        def maybeCategory = categoryRepository.findOneByName("brewery")
        TestUtils.equalsOptionalValue(maybeCategory, Category.of("brewery", [] as Set))
    }

    def "should return empty optional when there is no category with given id"() {
        expect:
        def maybeCategory = categoryRepository.findOneByName("fakeName")
        !maybeCategory.isPresent()
    }

    def "should ensure category"() {
        given:
        def name = "brewery"
        def value = "tyskie"

        when:
        categoryRepository.ensureCategory(name, value)

        then:
        def maybeCategory = categoryCrudRepository.findOneByName(name)
        TestUtils.equalsOptionalValue(maybeCategory, Category.of(name, [value] as Set))
        categoryCrudRepository.findAll().size() == 1
    }

    def "should add new value to existing category"() {
        given:
        def name = "brewery"
        def value = "tyskie"
        def existingValue = "lech"
        categoryCrudRepository.save(Category.of(name, [existingValue] as Set))

        when:
        categoryRepository.ensureCategory(name, value)

        then:
        def maybeCategory = categoryCrudRepository.findOneByName(name)
        TestUtils.equalsOptionalValue(maybeCategory, Category.of(name, [value, existingValue] as Set))
        categoryCrudRepository.findAll().size() == 1
    }

}
