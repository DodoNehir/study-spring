package com.example.ordercoffeedemo.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Response<Void> handleRuntimeException(RuntimeException e) {
        return Response.fail(e.getMessage());
    }
}
