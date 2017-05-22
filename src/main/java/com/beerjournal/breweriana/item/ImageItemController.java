package com.beerjournal.breweriana.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class ImageItemController {

    private final ImageItemService imageItemService;

    @GetMapping("items/{itemId}/images")
    public ResponseEntity<Set<String>> getItemImageIds(@PathVariable("itemId") String itemId) {
        return new ResponseEntity<>(imageItemService.getItemImageIds(itemId), HttpStatus.OK);
    }

    @PostMapping("users/{userId}/collection/items/{itemId}/images")
    public ResponseEntity<Map<String, String>> handleItemImageUpload(
            @PathVariable("userId") String userId,
            @PathVariable("itemId") String itemId,
            @RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(imageItemService.saveImageItem(file, itemId, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("{userId}/collection/items/{itemId}/images/{imageId}")
    public ResponseEntity<Map<String, String>> deleteItemImage(@PathVariable("userId") String userId,
                                             @PathVariable("itemId") String itemId,
                                             @PathVariable("imageId") String imageId) {
        return new ResponseEntity<>(imageItemService.deleteItemImage(itemId, imageId, userId), HttpStatus.OK);
    }
}
