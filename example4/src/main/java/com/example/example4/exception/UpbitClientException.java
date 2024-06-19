package com.example.example4.exception;

public class UpbitClientException extends RuntimeException {
    public UpbitClientException(String message) {
        super("Upbit 통신 중 에러 발생 : " + message);
    }
}