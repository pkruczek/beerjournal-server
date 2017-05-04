package com.beerjournal.breweriana.image

import com.beerjournal.breweriana.image.persistance.ImageRepository
import org.apache.commons.io.IOUtils
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
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

    @Autowired
    GridFsTemplate gridFsTemplate

    @Shared
    def filename = new ObjectId().toString()

    def "should save and read a file"() {
        when:
        def dbFilename = imageRepository.saveFile(srcImageInputStream(), filename, "image/png")
        def maybeDbStream = imageRepository.loadFile(filename)

        then:
        dbFilename == filename
        maybeDbStream.isPresent()
        IOUtils.contentEquals(srcImageInputStream(), maybeDbStream.get().getInputStream())
    }

    def "should delete a file"() {
        when:
        imageRepository.deleteFile(filename)
        def maybeDbStream = imageRepository.loadFile(filename)

        then:
        !maybeDbStream.isPresent()
        gridFsTemplate.find(new Query()).isEmpty()

        cleanup:
        mongoTemplate.db.dropDatabase()
    }

    def srcImageInputStream() {
        ImageRepositoryTest.class.getResourceAsStream("/static/tyskie.png")
    }

}
