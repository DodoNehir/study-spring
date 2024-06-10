package com.example.exresttemplate.controller;

import com.example.exresttemplate.exception.UpbitClientException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UpbitClientException.class)
    public String handleUpbitClientException(UpbitClientException e) {
        return e.getMessage();
    }
}
