package com.example.ordercoffeedemo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDto {
    private final String name;
    private final String phoneNumber;
    private final String address;
}
