package com.beerjournal.breweriana.image.item;

import com.beerjournal.breweriana.image.persistance.FileRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.utils.FileUtils;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

import static com.beerjournal.infrastructure.error.ErrorInfo.IMAGE_FORBIDDEN_MODIFICATION;


@Service
@RequiredArgsConstructor
public class ImageItemService {

    private final FileRepository fileRepository;
    private final ItemRepository itemRepository;
    private final SecurityUtils securityUtils;
    private final FileUtils fileUtils;

    Set<String> getItemImagesIds(String itemId) {
        return getItemInstance(itemId).getImages();
    }

    void saveImageItem(MultipartFile multipartFile, String itemId, String userId) throws IOException {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        String originalFilename = multipartFile.getOriginalFilename();

        if (!fileUtils.hasImageExtension(originalFilename) || !fileUtils.isImage(multipartFile))
            throw new BeerJournalException(ErrorInfo.UNSUPPORTED_IMAGE_EXTENSION);

        ObjectId imageId = fileRepository.saveFile(multipartFile.getInputStream(), originalFilename, multipartFile.getContentType());
        Item itemToUpdate = Item.copyWithOrWithoutImage(item, imageId.toString(), true);
        itemRepository.update(itemToUpdate);
    }

    GridFSDBFile loadImageItem(String imageId) {
        return fileRepository
                .loadFileById(imageId)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND));
    }

    void deleteImageItem(String itemId, String imageId, String userId) {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        if (!item.getImages().contains(imageId)) throw new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND);

        Item itemToUpdate = Item.copyWithOrWithoutImage(item, imageId, false);
        itemRepository.update(itemToUpdate);
        fileRepository.deleteFileById(imageId);
    }

    private Item getItemInstance(String itemId) {
        return itemRepository
                .findOneById(ServiceUtils.stringToObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }
}
