package com.beerjournal.infrastructure.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
@Getter
public enum ErrorInfo {

    // Collections
    USER_COLLECTION_NOT_FOUND(4000, "Collection not found for given owner id", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(4001, "Category not found for given owner id", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(4002, "User not found for given id", HttpStatus.NOT_FOUND),
    ITEM_NOT_FOUND(4003, "Item not found for given id", HttpStatus.NOT_FOUND),
    INCORRECT_USER_ID(4004, "Incorrect user ID", HttpStatus.BAD_REQUEST),
    USER_FORBIDDEN_MODIFICATION(4005, "You do not have permission to modify that user", HttpStatus.FORBIDDEN),
    COLLECTION_FORBIDDEN_MODIFICATION(4006, "You do not have permission to modify that collection", HttpStatus.FORBIDDEN),

    // Images
    REPEATED_IMAGE_NAME(4100, "Image name already assigned to item", HttpStatus.CONFLICT),
    UNSUPPORTED_IMAGE_EXTENSION(4101, "Incorrect image type", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND(4102, "Image not found", HttpStatus.NOT_FOUND);

    private final int code;
    private final String description;
    private final transient HttpStatus status;
}