package com.example.first.service;

import org.springframework.stereotype.Service;

// 어노테이션으로 Bean 등록
@Service
public class ProductService {
    public void createShoe(ProductDto productDto) {
        System.out.println("createShoe called");
    }

    public void createBag(ProductDto productDto) {
        System.out.println("createBag called");
    }

    public void createPants(ProductDto productDto) {
        System.out.println("createPants called");
    }
}
