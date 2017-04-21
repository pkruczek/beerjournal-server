package com.beerjournal.breweriana.persistence.config.error;

public class ClientErrorException extends RuntimeException {
    private ErrorInfo info;

    public ClientErrorException(ErrorInfo info) {
        this.info = info;
    }

    public ErrorInfo getInfo() {
        return info;
    }
}
