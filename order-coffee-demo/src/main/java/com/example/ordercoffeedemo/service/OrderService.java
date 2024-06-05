package com.example.ordercoffeedemo.service;

import com.example.ordercoffeedemo.domain.CreateOrder;
import com.example.ordercoffeedemo.domain.Order;
import com.example.ordercoffeedemo.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void newOrder(CreateOrder createOrder) {
        Order entity = Order.newOrder(createOrder);
        orderRepository.save(entity);
    }
}
