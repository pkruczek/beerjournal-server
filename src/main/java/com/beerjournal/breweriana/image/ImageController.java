package com.beerjournal.breweriana.image;

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
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<Set<String>> getImagesNames(@RequestParam("itemId") String itemId) throws IOException {
        return new ResponseEntity<>(imageService.getItemImagesNames(itemId), HttpStatus.OK);
    }

    @PostMapping(value = "{itemId}")
    public ResponseEntity<?> handleImageUpload(@RequestParam("file") MultipartFile file,
                                               @PathVariable("itemId") String itemId) throws IOException {
        imageService.saveImage(file, itemId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "{itemId}")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam("imageName") String imageName,
                                                        @PathVariable("itemId") String itemId) {
        GridFSDBFile image = imageService.loadImage(itemId, imageName);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType((image.getContentType())))
                .body(new InputStreamResource(image.getInputStream()));
    }

    @DeleteMapping(value = "{itemId}")
    public ResponseEntity<?> deleteImage(@RequestParam("imageName") String imageName,
                                         @PathVariable("itemId") String itemId) {
        imageService.deleteImage(itemId, imageName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
