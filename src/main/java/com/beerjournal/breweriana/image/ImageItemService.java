package com.beerjournal.breweriana.image;

import com.beerjournal.breweriana.image.persistance.FileRepository;
import com.beerjournal.breweriana.item.persistence.Item;
import com.beerjournal.breweriana.item.persistence.ItemRepository;
import com.beerjournal.breweriana.utils.ServiceUtils;
import com.beerjournal.infrastructure.config.ApplicationProperties;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageItemService {

    private final ApplicationProperties properties;
    private final FileRepository fileRepository;
    private final ItemRepository itemRepository;

    Set<String> getItemImagesNames(String itemId) {
        return getItemInstance(itemId).getImages();
    }

    void saveImage(MultipartFile multipartFile, String itemId) throws IOException {
        Item item = getItemInstance(itemId);
        String originalFilename = multipartFile.getOriginalFilename();

        if (item.getImages().contains(originalFilename)) throw new BeerJournalException(ErrorInfo.REPEATED_IMAGE_NAME);
        if (!hasImageExtension(originalFilename) || !isImage(multipartFile)) throw new BeerJournalException(ErrorInfo.UNSUPPORTED_IMAGE_EXTENSION);

        fileRepository.saveFile(multipartFile.getInputStream(), itemId + multipartFile.getOriginalFilename(), multipartFile.getContentType());
        item.getImages().add(originalFilename);
        itemRepository.save(item);
    }

    GridFSDBFile loadImage(String itemId, String imageName) {
        return fileRepository
                .loadFile(itemId + imageName)
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND));
    }

    void deleteImage(String itemId, String imageName) {
        Item item = getItemInstance(itemId);
        if (!item.getImages().contains(imageName)) throw new BeerJournalException(ErrorInfo.IMAGE_NOT_FOUND);

        item.getImages().remove(imageName);
        itemRepository.save(item);
        fileRepository.deleteFile(itemId + imageName);
    }

    private boolean hasImageExtension(String fileName) {
        return FilenameUtils.isExtension(fileName.toLowerCase(), properties.getAcceptedImageExtensions());
    }

    private boolean isImage(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()) != null;
    }

    private Item getItemInstance(String itemId) {
        return itemRepository
                .findOneById(ServiceUtils.stringToObjectId(itemId))
                .orElseThrow(() -> new BeerJournalException(ErrorInfo.ITEM_NOT_FOUND));
    }
}
