package com.beerjournal.utils;

public class ClientErrorException extends RuntimeException {
    private ErrorInfo info;

    public ClientErrorException(ErrorInfo info) {
        this.info = info;
    }

    public ErrorInfo getInfo() {
        return info;
    }
}
