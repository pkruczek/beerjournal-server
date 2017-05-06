package com.beerjournal.breweriana.user;

import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}/avatar")
@RequiredArgsConstructor
class ImageUserController {

    private final ImageUserService imageUserService;

    @PostMapping
    public ResponseEntity<Map<String, String>> handleUserAvatarUpload(@RequestParam("file") MultipartFile file,
                                                                      @PathVariable("userId") String userId) throws IOException {
        return new ResponseEntity<>(imageUserService.saveUserAvatar(file, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getUserAvatar(@PathVariable("userId") String userId) {
        GridFSDBFile image = imageUserService.loadUserAvatar(userId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType((image.getContentType())))
                .body(new InputStreamResource(image.getInputStream()));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteUserAvatar(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(imageUserService.deleteUserAvatar(userId), HttpStatus.OK);
    }
}
