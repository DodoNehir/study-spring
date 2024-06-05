package com.example.ordercoffeedemo.repository;

import com.example.ordercoffeedemo.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
