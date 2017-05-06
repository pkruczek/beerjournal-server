package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.file.persistence.FileRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.breweriana.utils.ImageValidator;
import com.beerjournal.breweriana.utils.SecurityUtils;
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
class ImageItemService {

    private final FileRepository fileRepository;
    private final ItemRepository itemRepository;
    private final SecurityUtils securityUtils;
    private final ImageValidator imageValidator;

    Set<String> getItemImagesIds(String itemId) {
        return getItemInstance(itemId).getImages();
    }

    void saveImageItem(MultipartFile multipartFile, String itemId, String userId) throws IOException {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        String originalFilename = multipartFile.getOriginalFilename();

        if (!imageValidator.hasImageExtension(originalFilename) || !imageValidator.isImage(multipartFile))
            throw new BeerJournalException(ErrorInfo.UNSUPPORTED_IMAGE_EXTENSION);

        ObjectId imageId = fileRepository.saveFile(multipartFile.getInputStream(), originalFilename, multipartFile.getContentType());
        Item itemToUpdate = item.withNewImage(imageId.toString());
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

        Item itemToUpdate = item.withoutImage(imageId);
        itemRepository.update(itemToUpdate);
        fileRepository.deleteFileById(imageId);
    }

    private Item getItemInstance(String itemId) {
        return itemRepository
                .findOneById(Converters.toObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }
}
