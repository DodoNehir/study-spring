package com.example.ordercoffeedemo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomer {
    private final String name;
    private final String address;
    private final String phoneNumber;
}
