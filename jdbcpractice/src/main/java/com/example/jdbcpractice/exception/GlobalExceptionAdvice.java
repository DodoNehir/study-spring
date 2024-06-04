package com.example.jdbcpractice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public String handleRuntimeException(RuntimeException re){
        return re.getMessage();
    }

    @ExceptionHandler(value = SampleNotValidException.class)
    public String handleSampleNotValidException(SampleNotValidException e){
        return e.getMessage();
    }
}
