package com.example.exresttemplate.exception;

public class UpbitClientException extends RuntimeException {
    public UpbitClientException(String message) {
        super(String.format("Upbit와 통신 중 에러 발생: %s", message));
    }
}
