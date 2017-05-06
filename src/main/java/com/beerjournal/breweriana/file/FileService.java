package com.beerjournal.breweriana.file;


import com.beerjournal.breweriana.file.persistence.FileRepository;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.FILE_NOT_FOUND;

@Service
@RequiredArgsConstructor
class FileService {

    private final FileRepository fileRepository;

    GridFSDBFile getFile(String itemId) {
        return fileRepository
                .loadFileById(Converters.toObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(FILE_NOT_FOUND));
    }

}
