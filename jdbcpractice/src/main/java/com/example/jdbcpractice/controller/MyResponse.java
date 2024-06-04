package com.example.jdbcpractice.controller;

public class MyResponse<T> {
    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    public MyResponse(boolean success,
                      String code,
                      String message,
                      T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> MyResponse<T> success(T data) {
        return new MyResponse<>(true,
                "200",
                null,
                data);
    }

    public static <T> MyResponse<T> fail(String message) {
        return new MyResponse<>(false,
                "500",
                message,
                null);
    }
}
