package com.smartpace.smartpace.exception;

public class Concept2ApiException extends RuntimeException {

    private final int statusCode;

    public Concept2ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;

    }

    public Concept2ApiException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
