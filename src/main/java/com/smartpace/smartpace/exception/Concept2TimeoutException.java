package com.smartpace.smartpace.exception;

public class Concept2TimeoutException extends Concept2ApiException{

    public Concept2TimeoutException(String message, int statusCode) {
        super(message, statusCode);
    }

    public Concept2TimeoutException(String message, Throwable cause, int statusCode) {
        super(message, cause, statusCode);
    }

}
