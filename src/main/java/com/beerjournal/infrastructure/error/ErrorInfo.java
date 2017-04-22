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
    USER_COLLECTION_NOT_FOUND(4000, "Collection not found for given owner id", HttpStatus.NOT_FOUND);

    private final int code;
    private final String description;
    private final transient HttpStatus status;
}