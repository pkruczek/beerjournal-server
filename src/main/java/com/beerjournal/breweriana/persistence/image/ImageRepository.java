package com.beerjournal.breweriana.persistence.image;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final GridFsOperations gridFsOperations;

    public String saveFile(InputStream inputStream, String contentType) {
        GridFSFile file = gridFsOperations.store(inputStream, UUID.randomUUID().toString(), contentType);
        return file.getFilename();
    }

    public Optional<InputStream> loadFile(String filename) {
        return Optional.ofNullable(gridFsOperations.findOne(byFilename(filename)))
                .map(GridFSDBFile::getInputStream);

    }

    public void deleteFile(String filename) {
        gridFsOperations.delete(byFilename(filename));
    }

    private Query byFilename(String filename) {
        return new Query().addCriteria(Criteria.where("filename").is(filename));
    }

}
