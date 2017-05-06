package com.beerjournal.breweriana.item;

import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class ImageItemController {

    private final ImageItemService imageItemService;

    @GetMapping("items/{itemId}/images")
    public ResponseEntity<Set<String>> getImageIds(@RequestParam("itemId") String itemId) {
        return new ResponseEntity<>(imageItemService.getItemImageIds(itemId), HttpStatus.OK);
    }

    @PostMapping("users/{userId}/collection/items/{itemId}/images")
    public ResponseEntity<?> handleImageItemUpload(
            @PathVariable("userId") String userId,
            @PathVariable("itemId") String itemId,
            @RequestParam("file") MultipartFile file) throws IOException {

        imageItemService.saveImageItem(file, itemId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("images/{imageId}")
    public ResponseEntity<InputStreamResource> getImageItem(@PathVariable("imageId") String imageId) {
        GridFSDBFile image = imageItemService.loadImageItem(imageId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType((image.getContentType())))
                .body(new InputStreamResource(image.getInputStream()));
    }

    @DeleteMapping("{userId}/collection/items/{itemId}/images/{imageId}")
    public ResponseEntity<?> deleteItemImage(@PathVariable("userId") String userId,
                                             @PathVariable("itemId") String itemId,
                                             @PathVariable("imageId") String imageId) {
        imageItemService.deleteImageItem(itemId, imageId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
