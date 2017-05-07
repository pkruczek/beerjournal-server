package com.beerjournal.breweriana.file;

import com.google.common.io.ByteStreams;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("files/{fileId}")
    public ResponseEntity<byte[]> getImageItem(@PathVariable("fileId") String fileId) throws IOException {
        GridFSDBFile file = fileService.getFile(fileId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType((file.getContentType())))
                .body(Base64.getEncoder().encode(ByteStreams.toByteArray(file.getInputStream())));
    }

}
