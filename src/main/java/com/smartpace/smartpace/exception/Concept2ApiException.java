package com.smartpace.smartpace.exception;

public class Concept2ApiException extends RuntimeException{

    private final int statustCode;

    public Concept2ApiException(String message, int statusCode){
        super(message);
        this.statustCode = statusCode;

    }


    public Concept2ApiException(String message, Throwable cause, int statusCode){
        super(message,cause);
        this.statustCode = statusCode;
    }

    public int getStatustCode() {
        return statustCode;
    }
}
