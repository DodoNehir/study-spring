package com.example.ordercoffeedemo.service;

import com.example.ordercoffeedemo.domain.CreateCustomer;
import com.example.ordercoffeedemo.domain.Customer;
import com.example.ordercoffeedemo.domain.CustomerDto;
import com.example.ordercoffeedemo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDto newCustomer(CreateCustomer createCustomer) {
        Customer entity = Customer.newCustomer(createCustomer);
        customerRepository.save(entity);
        return CustomerDto.builder()
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .build();
    }
}
