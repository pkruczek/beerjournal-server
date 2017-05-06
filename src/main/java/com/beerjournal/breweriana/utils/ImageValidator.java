package com.beerjournal.breweriana.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public final class ImageValidator {

    private final List<String> acceptedImageExtensions;

    @Autowired
    public ImageValidator(@Value("${bj.app.images.extensions}") final String extensions) {
        acceptedImageExtensions = Arrays.asList(extensions.split(","));
    }

    public boolean hasImageExtension(String fileName) {
        return FilenameUtils.isExtension(fileName.toLowerCase(), acceptedImageExtensions);
    }

    public boolean isImage(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()) != null;
    }
}
