package com.letscode.starwarsapiwebflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RebelControlerAdvice {

    @ExceptionHandler(TradeException.class)
    public ResponseEntity<ExceptionMessage> tradeError(TradeException e){
        ExceptionMessage errorMessage = ExceptionMessage.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("A transferencia de itens não pode ser realizada!")
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
