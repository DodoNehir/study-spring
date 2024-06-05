package com.example.ordercoffeedemo.controller;

import com.example.ordercoffeedemo.domain.CreateCustomer;
import com.example.ordercoffeedemo.domain.CustomerDto;
import com.example.ordercoffeedemo.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/api/v1/customers")
    public Response<CustomerDto> createNewCustomer(
            @RequestParam String name,
            @RequestParam String phoneNumber,
            @RequestParam String address
    ) {


        return Response.success(customerService.newCustomer(
                CreateCustomer.builder()
                        .name(name)
                        .phoneNumber(phoneNumber)
                        .address(address)
                        .build()
        ));

//        return customerService.newCustomer(
//                CreateCustomer.builder()
//                        .name(name)
//                        .phoneNumber(phoneNumber)
//                        .address(address)
//                        .build()
//        );

//        return Customer.newCustomer(
//                CreateCustomer.builder()
//                        .name(name)
//                        .phoneNumber(phoneNumber)
//                        .address(address)
//                        .build()
//        );
    }
}
