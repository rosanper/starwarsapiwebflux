package com.letscode.starwarsapiwebflux.exception;

public class NotFoundRebelException extends RuntimeException{
    public NotFoundRebelException(String message) {
        super(message);
    }
}
