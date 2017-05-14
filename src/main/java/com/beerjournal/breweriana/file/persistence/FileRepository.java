package com.beerjournal.breweriana.file.persistence;

import com.beerjournal.breweriana.utils.Converters;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final GridFsOperations gridFsOperations;

    public ObjectId saveFile(InputStream inputStream, String filename, String contentType) {
        GridFSFile file = gridFsOperations.store(inputStream, filename, contentType);
        return Converters.toObjectId(file.getId().toString());
    }

    public Optional<GridFSDBFile> loadFileByFilename(String filename) {
        return Optional.ofNullable(gridFsOperations.findOne(byFilename(filename)));
    }

    public Optional<GridFSDBFile> loadFileById(ObjectId id) {
        return Optional.ofNullable(gridFsOperations.findOne(byId(id)));
    }

    public void deleteFileByFilename(String filename) {
        gridFsOperations.delete(byFilename(filename));
    }

    public void deleteFileById(ObjectId id) {
        gridFsOperations.delete(byId(id));
    }

    private Query byId(ObjectId id) {
        return new Query().addCriteria(Criteria.where("_id").is(id));
    }

    private Query byFilename(String filename) {
        return new Query().addCriteria(Criteria.where("filename").is(filename));
    }

}
