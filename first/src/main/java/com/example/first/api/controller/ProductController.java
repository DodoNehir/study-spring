package com.example.first.api.controller;

import com.example.first.service.ProductDto;
import com.example.first.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/product/shoe")
    public void createShoe() {
        productService.createShoe(new ProductDto("신발", "shoe"));
    }

    @GetMapping("/api/product/bag")
    public void createBag() {
        productService.createBag(new ProductDto("bag", "bag"));
    }

    @GetMapping("/api/product/pants")
    public void createPants() {
        productService.createPants(new ProductDto("pants", "pants"));
    }

}
