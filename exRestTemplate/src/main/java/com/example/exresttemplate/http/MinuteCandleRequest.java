package com.example.exresttemplate.http;

import lombok.Getter;

@Getter
public class MinuteCandleRequest {
    private String market;
    private int count;
}
