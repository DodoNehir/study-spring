package com.example.jdbcpractice.exception;

import com.example.jdbcpractice.controller.MyResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public MyResponse<String> handleRuntimeException(RuntimeException re){
        return MyResponse.fail(re.getMessage());
    }

    @ExceptionHandler(value = SampleNotValidException.class)
    public MyResponse<String> handleSampleNotValidException(SampleNotValidException e){
        return MyResponse.fail(e.getMessage());
    }
}
