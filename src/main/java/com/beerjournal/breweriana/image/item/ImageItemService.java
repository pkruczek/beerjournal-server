package com.beerjournal.breweriana.image.item;

import com.beerjournal.breweriana.image.persistance.FileRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.config.ApplicationProperties;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Set;

import static com.beerjournal.infrastructure.error.ErrorInfo.IMAGE_FORBIDDEN_MODIFICATION;


@Service
@RequiredArgsConstructor
public class ImageItemService {

    private final FileRepository fileRepository;
    private final ItemRepository itemRepository;
    private final SecurityUtils securityUtils;

    Set<ObjectId> getItemImagesIds(String itemId) {
        return getItemInstance(itemId).getImages();
    }

    void saveImageItem(MultipartFile multipartFile, String itemId, String userId) throws IOException {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        String originalFilename = multipartFile.getOriginalFilename();

        if (!fileRepository.hasImageExtension(originalFilename) || !fileRepository.isImage(multipartFile))
            throw new BeerJournalException(ErrorInfo.UNSUPPORTED_IMAGE_EXTENSION);

        ObjectId id = fileRepository.saveFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
        item.getImages().add(id);
        itemRepository.save(item);
    }

    GridFSDBFile loadImageItem(ObjectId id) {
        return fileRepository
                .loadFileById(id)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND));
    }

    void deleteImageItem(String itemId, ObjectId id, String userId) {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        if (!item.getImages().contains(id)) throw new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND);

        item.getImages().remove(id);
        itemRepository.save(item);
        fileRepository.deleteFileById(id);
    }

    private Item getItemInstance(String itemId) {
        return itemRepository
                .findOneById(ServiceUtils.stringToObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }
}
