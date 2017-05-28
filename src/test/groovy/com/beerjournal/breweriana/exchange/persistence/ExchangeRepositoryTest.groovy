package com.beerjournal.breweriana.exchange.persistence

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.util.stream.Collectors

import static com.beerjournal.breweriana.utils.TestUtils.simpleItemRef

@SpringBootTest
@ActiveProfiles("test")
class ExchangeRepositoryTest extends Specification {

    @Autowired
    ExchangeCrudRepository exchangeCrudRepository

    @Autowired
    ExchangeRepository exchangeRepository

    @Autowired
    MongoTemplate mongoTemplate

    def someItemId = new ObjectId()
    def someUserId = new ObjectId()

    def someExchange = ExchangeOffer.of(someUserId, new ObjectId(), [simpleItemRef(someItemId)] as Set, [simpleItemRef()] as Set,
            ExchangeState.WAITING_FOR_OFFEROR)

    def someExchanges = [
            someExchange,
            ExchangeOffer.of(someUserId, new ObjectId(), [simpleItemRef()] as Set, [simpleItemRef()] as Set, ExchangeState.WAITING_FOR_OFFEROR),
            ExchangeOffer.of(new ObjectId(), new ObjectId(), [simpleItemRef()] as Set, [simpleItemRef()] as Set, ExchangeState.WAITING_FOR_OFFEROR)
    ] as Set

    def setup() {
        exchangeCrudRepository.save(someExchanges)
    }

    def cleanup() {
        mongoTemplate.db.dropDatabase()
    }

    def "should find exchange by offerorId and desiredItemId"() {
        when:
        def result = exchangeRepository.findAllByOfferorIdAndItemId(someUserId, someItemId).collect(Collectors.toSet())

        then:
        result.size() == 1
        result.contains(someExchange)
    }

    def "should return empty list for itemId in exchange but not correct offerorId"() {
        when:
        def result = exchangeRepository.findAllByOfferorIdAndItemId(new ObjectId(), someItemId).collect(Collectors.toSet())

        then:
        result.isEmpty()
    }

}
