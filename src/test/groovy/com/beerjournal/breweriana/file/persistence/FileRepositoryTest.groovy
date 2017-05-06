package com.beerjournal.breweriana.file.persistence

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
class FileRepositoryTest extends Specification {

    @Autowired
    FileRepository fileRepository

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    GridFsTemplate gridFsTemplate

    @Shared
    def filename = new ObjectId().toString()

    def "should save and read a file"() {
        when:
        def dbId = fileRepository.saveFile(srcImageInputStream(), filename, "file/png")
        def maybeDbStream = fileRepository.loadFileById(dbId)

        then:
        maybeDbStream.isPresent()
        maybeDbStream.get().getFilename() == filename
        IOUtils.contentEquals(srcImageInputStream(), maybeDbStream.get().getInputStream())
    }

    def "should delete a file"() {
        when:
        fileRepository.deleteFileByFilename(filename)
        def maybeDbStream = fileRepository.loadFileByFilename(filename)

        then:
        !maybeDbStream.isPresent()
        gridFsTemplate.find(new Query()).isEmpty()

        cleanup:
        mongoTemplate.db.dropDatabase()
    }

    def srcImageInputStream() {
        FileRepositoryTest.class.getResourceAsStream("/static/tyskie.png")
    }

}
