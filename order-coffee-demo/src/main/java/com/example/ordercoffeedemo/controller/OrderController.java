package com.example.ordercoffeedemo.controller;

import com.example.ordercoffeedemo.domain.CreateOrder;
import com.example.ordercoffeedemo.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public Response<Void> newOrder() {

        HashMap<Integer, Integer> orderMap = new HashMap<>();
        orderMap.put(1, 5); // 1번 상품 5개
        orderMap.put(2, 8); // 2번 상풀 8개

        orderService.newOrder(CreateOrder.builder()
                .customerId(1)
                .quantityByProduct(orderMap)
                .build());
        return Response.success(null);
    }
}
