package com.example.jdbcpractice.exception;

public class SampleNotValidException  extends RuntimeException{
    public SampleNotValidException(String message) {
        super(message);
    }
}
