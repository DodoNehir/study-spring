package com.example.ordercoffeedemo.repository;

import com.example.ordercoffeedemo.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
