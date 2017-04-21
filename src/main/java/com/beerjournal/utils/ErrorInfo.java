package com.beerjournal.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorInfo {

    // Categories
    NOT_USER_COLLECTION_FOUND(4000, "No user collection founded for given order id", HttpStatus.CONFLICT),

    //Other exceptions
    CONFLICT(4999, "Conflict", HttpStatus.CONFLICT);

    private final int errCode;
    private final String errDescr;
    private final HttpStatus status;

    ErrorInfo(int errCode, String errDescr, HttpStatus status) {
        this.errCode = errCode;
        this.errDescr = errDescr;
        this.status = status;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrDescr() {
        return errDescr;
    }

    @JsonIgnore
    public HttpStatus getStatus() {
        return status;
    }
}