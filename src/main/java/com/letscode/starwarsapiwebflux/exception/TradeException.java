package com.letscode.starwarsapiwebflux.exception;

public class TradeException extends RuntimeException{
    public TradeException(String message) {
        super(message);
    }
}
