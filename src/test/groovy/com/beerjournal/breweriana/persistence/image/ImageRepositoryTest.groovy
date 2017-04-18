package com.beerjournal.breweriana.persistence.image

import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@SpringBootTest
@ActiveProfiles("test")
@Stepwise
class ImageRepositoryTest extends Specification {

    @Autowired
    ImageRepository imageRepository

    @Autowired
    MongoTemplate mongoTemplate

    @Shared
    def filename

    def "should save and read a file"() {
        when:
        filename = imageRepository.saveFile(srcImageInputStream(), "image/png")
        def maybeDbStream = imageRepository.loadFile(filename)

        then:
        maybeDbStream.isPresent()
        IOUtils.contentEquals(srcImageInputStream(), maybeDbStream.get())
    }

    def "should delete a file"() {
        when:
        imageRepository.deleteFile(filename)
        def maybeDbStream = imageRepository.loadFile(filename)

        then:
        !maybeDbStream.isPresent()

        cleanup:
        mongoTemplate.db.dropDatabase()
    }

    def srcImageInputStream() {
        ImageRepositoryTest.class.getResourceAsStream("/static/tyskie.png")
    }

}
