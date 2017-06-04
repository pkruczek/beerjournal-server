package com.beerjournal.breweriana.item;

import com.beerjournal.breweriana.file.persistence.FileRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.utils.Converters;
import com.beerjournal.breweriana.utils.ImageValidator;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.beerjournal.infrastructure.error.ErrorInfo.IMAGE_FORBIDDEN_MODIFICATION;


@Service
@RequiredArgsConstructor
class ImageItemService {

    private final FileRepository fileRepository;
    private final ItemRepository itemRepository;
    private final SecurityUtils securityUtils;
    private final ImageValidator imageValidator;

    Set<String> getItemImageIds(String itemId) {
        return Converters.toStringIds(getItemInstance(itemId).getImageIds())
                .collect(Collectors.toSet());
    }

    Map<String, String> getItemMainImageId(String itemId) {
        return Converters.toMap(getItemInstance(itemId).getMainImageId());
    }

    Map<String, String> saveImageItem(MultipartFile multipartFile, String itemId, String userId) throws IOException {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        ObjectId imageId = saveImage(multipartFile);
        Item itemToUpdate = item
                .withNewImageId(imageId)
                .withMainImageId();
        itemRepository.update(itemToUpdate);
        return Converters.toMap(imageId);
    }

    Map<String, String> saveMainImageItem(MultipartFile multipartFile, String itemId, String userId) throws IOException {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        Item item = getItemInstance(itemId);
        ObjectId imageId = saveImage(multipartFile);
        Item itemToUpdate = item
                .withNewImageId(imageId)
                .withMainImageId(imageId);
        itemRepository.update(itemToUpdate);
        return Converters.toMap(imageId);
    }

    Map<String, String> deleteItemImage(String itemId, String imageStringId, String userId) {
        if (!securityUtils.checkIfAuthorized(userId)) throw new BeerJournalException(IMAGE_FORBIDDEN_MODIFICATION);

        ObjectId imageId = Converters.toObjectId(imageStringId);
        Item item = getItemInstance(itemId);
        if (!item.getImageIds().contains(imageId)) throw new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND);

        Item itemToUpdate = item.withoutImageId(imageId);
        itemRepository.update(itemToUpdate);
        fileRepository.deleteFileById(imageId);
        return Converters.toMap(imageId);
    }

    private ObjectId saveImage(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        if (!imageValidator.hasImageExtension(originalFilename) || !imageValidator.isImage(multipartFile))
            throw new BeerJournalException(ErrorInfo.UNSUPPORTED_IMAGE_TYPE);

        return fileRepository.saveFile(multipartFile.getInputStream(), originalFilename, multipartFile.getContentType());
    }

    private Item getItemInstance(String itemId) {
        return itemRepository
                .findOneById(Converters.toObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }

}
