package com.beerjournal.breweriana.image.user;

import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images/user")
@RequiredArgsConstructor
public class ImageUserController {

    private final ImageUserService imageUserService;

    @PostMapping(value = "{userId}")
    public ResponseEntity<?> handleImageUserUpload(@RequestParam("file") MultipartFile file,
                                                   @PathVariable("userId") String userId) throws IOException {
        imageUserService.saveUserAvatarImage(file, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<InputStreamResource> getImageUser(@PathVariable("userId") String userId) {
        GridFSDBFile image = imageUserService.loadUserAvatarImage(userId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType((image.getContentType())))
                .body(new InputStreamResource(image.getInputStream()));
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<?> deleteItemUser(@PathVariable("userId") String userId) {
        imageUserService.deleteUserAvatarImage(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
