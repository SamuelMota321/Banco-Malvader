package com.BancoMalvader.Java_Api.infra;

import com.BancoMalvader.Java_Api.exceptions.ClientNotFoundException;
import com.BancoMalvader.Java_Api.exceptions.InsufficientBalanceException;
import com.BancoMalvader.Java_Api.exceptions.LimitExcepton;
import com.BancoMalvader.Java_Api.exceptions.TransactionsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    private ResponseEntity<RestErrorMessage> clientNotFoundHandler(ClientNotFoundException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(threatResponse);
    }

    @ExceptionHandler(TransactionsNotFoundException.class)
    private ResponseEntity<RestErrorMessage> transactionNotFoundHandler(TransactionsNotFoundException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(threatResponse);
    }

    @ExceptionHandler(LimitExcepton.class)
    private ResponseEntity<RestErrorMessage> limitHandler(LimitExcepton exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(threatResponse);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    private ResponseEntity<RestErrorMessage> insufficientBalanceHandler(InsufficientBalanceException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(threatResponse);
    }


}
