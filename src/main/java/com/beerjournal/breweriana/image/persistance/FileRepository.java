package com.beerjournal.breweriana.image.persistance;

import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.config.ApplicationProperties;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final GridFsOperations gridFsOperations;
    private final ApplicationProperties properties;

    public ObjectId saveFile(InputStream inputStream, String filename, String contentType) {
        GridFSFile file = gridFsOperations.store(inputStream, filename, contentType);
        return ServiceUtils.stringToObjectId(file.getId().toString());
    }

    public Optional<GridFSDBFile> loadFileByFilename(String filename) {
        return Optional.ofNullable(gridFsOperations.findOne(byFilename(filename)));
    }

    public Optional<GridFSDBFile> loadFileById(ObjectId id) {
        return Optional.ofNullable(gridFsOperations.findOne(byId(id)));
    }

    public void deleteFileByFileNAME(String filename) {
        gridFsOperations.delete(byFilename(filename));
    }

    public void deleteFileById(ObjectId id) {
        gridFsOperations.delete(byId(id));
    }

    public boolean hasImageExtension(String fileName) {
        return FilenameUtils.isExtension(fileName.toLowerCase(), properties.getAcceptedImageExtensions());
    }

    public boolean isImage(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()) != null;
    }

    private Query byId(Object id) {
        return new Query().addCriteria(Criteria.where("_id").is(id));
    }

    private Query byFilename(String filename) {
        return new Query().addCriteria(Criteria.where("filename").is(filename));
    }

}
