package com.beerjournal.breweriana.image.item;

import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/images/item")
@RequiredArgsConstructor
public class ImageItemController {

    private final ImageItemService imageItemService;

    @GetMapping
    public ResponseEntity<Set<ObjectId>> getImagesIds(@RequestParam("itemId") String itemId) {
        return new ResponseEntity<>(imageItemService.getItemImagesIds(itemId), HttpStatus.OK);
    }

    @PostMapping(value = "{itemId}")
    public ResponseEntity<?> handleImageItemUpload(@PathVariable("itemId") String itemId,
                                                   @RequestParam("file") MultipartFile file,
                                                   @RequestParam("userId") String userId) throws IOException {
        imageItemService.saveImageItem(file, itemId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "{imageId}")
    public ResponseEntity<InputStreamResource> getImageItem(@PathVariable("imageId") ObjectId imageId) {
        GridFSDBFile image = imageItemService.loadImageItem(imageId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType((image.getContentType())))
                .body(new InputStreamResource(image.getInputStream()));
    }

    @DeleteMapping(value = "{itemId}/{imageId}")
    public ResponseEntity<?> deleteItemImage(@PathVariable("imageId") ObjectId imageId,
                                             @PathVariable("itemId") String itemId,
                                             @RequestParam("userId") String userId) {
        imageItemService.deleteImageItem(itemId, imageId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
