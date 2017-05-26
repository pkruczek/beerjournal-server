package com.beerjournal.breweriana.file;

import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import static java.util.concurrent.TimeUnit.DAYS;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final static String VERSION = "123";

    private final FileService fileService;

    @GetMapping("files/{fileId}")
    public ResponseEntity<InputStreamResource> getImageItem(WebRequest webRequest,
                                                            @PathVariable("fileId") String fileId) {
        if (webRequest.checkNotModified(VERSION)) {
            return null;
        }

        GridFSDBFile file = fileService.getFile(fileId);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(365, DAYS))
                .eTag(VERSION)
                .contentType(MediaType.parseMediaType((file.getContentType())))
                .body(new InputStreamResource(file.getInputStream()));
    }

}
