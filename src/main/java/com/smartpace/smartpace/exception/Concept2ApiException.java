package com.smartpace.smartpace.exception;

public class Concept2ApiException extends RuntimeException{

    public Concept2ApiException(String message){
        super(message);

    }


    public Concept2ApiException(String message, Throwable cause){
        super(message,cause);
    }
}
