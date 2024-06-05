package com.example.ordercoffeedemo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    private int customerId;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    public Customer(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Customer를 반환하는 static method. 새로운 Customer를 만든다.
    public static Customer newCustomer(CreateCustomer createCustomer) {
        return new Customer(
                createCustomer.getName(),
                createCustomer.getPhoneNumber(),
                createCustomer.getAddress()
        );
    }
}
