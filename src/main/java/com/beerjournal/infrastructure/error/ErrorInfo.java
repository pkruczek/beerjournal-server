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
    MALFORMED_ID(4004, "Not a proper id", HttpStatus.BAD_REQUEST),
    USER_FORBIDDEN_MODIFICATION(4005, "You do not have permission to modify that user", HttpStatus.FORBIDDEN),
    COLLECTION_FORBIDDEN_MODIFICATION(4006, "You do not have permission to modify that collection", HttpStatus.FORBIDDEN),
    USER_DUPLICATE_EMAIL(4007, "The account with given email already exists", HttpStatus.CONFLICT),
    USER_INCORRECT_PASSWORD(4008, "Provided password does not match current password", HttpStatus.FORBIDDEN),
    USER_INCORRECT_EMAIL(4009, "Provided email does not match current email", HttpStatus.FORBIDDEN),

    // Images
    UNSUPPORTED_IMAGE_TYPE(4100, "Incorrect image type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    IMAGE_NOT_FOUND(4101, "Image not found", HttpStatus.NOT_FOUND),
    IMAGE_FORBIDDEN_MODIFICATION(4102, "You do not have permission to modify that item's file", HttpStatus.FORBIDDEN),

    // Files
    FILE_NOT_FOUND(4103, "File not found", HttpStatus.NOT_FOUND),

    // Server
    INCORRECT_EVENT_DATA_TYPE(5000, "Data type in given event not found", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String description;
    private final transient HttpStatus status;
}