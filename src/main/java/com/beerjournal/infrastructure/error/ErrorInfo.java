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

    INCORRECT_USER_ID(4004, "Incorrect user ID", HttpStatus.BAD_REQUEST);



    private final int code;
    private final String description;
    private final transient HttpStatus status;
}