package com.beerjournal.breweriana.user.persistence

import com.beerjournal.breweriana.collection.persistence.UserCollection
import com.beerjournal.breweriana.collection.persistence.UserCollectionCrudRepository
import com.beerjournal.breweriana.utils.TestUtils
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest extends Specification {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserCrudRepository userCrudRepository

    @Autowired
    UserCollectionCrudRepository userCollectionCrudRepository

    @Autowired
    MongoTemplate mongoTemplate

    def setup() {
        mongoTemplate.db.dropDatabase()
    }

    def someUser = TestUtils.someUser()

    def "should save a user"() {
        when:
        userRepository.save(someUser)

        then:
        def maybeUser = userCrudRepository.findOneByEmail(someUser.email)
        TestUtils.equalsOptionalValue(maybeUser, someUser)
    }

    def "should save an empty collection with a user"() {
        when:
        def savedUser = userRepository.save(someUser)

        then:
        def maybeCollection = userCollectionCrudRepository.findOneByOwnerId(savedUser.id)
        TestUtils.equalsOptionalValue(maybeCollection, UserCollection.of(savedUser.id, [] as Set))
    }

    def "should find a user by id"() {
        setup:
        def savedUser = userCrudRepository.save(someUser)

        expect:
        def maybeUser = userRepository.findOneById(savedUser.id)
        TestUtils.equalsOptionalValue(maybeUser, someUser)
    }

    def "should return an empty optional when there is no user with given id"() {
        expect:
        def maybeUser = userRepository.findOneById(new ObjectId())
        !maybeUser.isPresent()
    }

    def "should find a user by email"() {
        setup:
        def savedUser = userCrudRepository.save(someUser)

        expect:
        def maybeUser = userRepository.findOneByEmail(savedUser.email)
        TestUtils.equalsOptionalValue(maybeUser, someUser)
    }

    def "should return an empty optional when there is no user with given email"() {
        expect:
        def maybeUser = userRepository.findOneByEmail("fake@email.com")
        !maybeUser.isPresent()
    }

    def "should return empty list when there is not any user"() {
        expect:
        userRepository.findByFirstNameStartsWith(0, 10, "").getContent().isEmpty()
    }

    def "should find all users"() {
        setup:
        userCrudRepository.save(TestUtils.someUsers())

        expect:
        userRepository.findByFirstNameStartsWith(0, 10, "").getContent() as Set == TestUtils.someUsers()
    }

    def "should find correct page details for users that firstName that starts with 'Ja'"() {
        setup:
        userCrudRepository.save(TestUtils.someUsers())
        userCrudRepository.save(TestUtils.someUser())

        expect:
        def with = userRepository.findByFirstNameStartsWith(0, 1, "Ja")
        with.getTotalElements() == 2 && with.getTotalPages() == 2
    }

}
