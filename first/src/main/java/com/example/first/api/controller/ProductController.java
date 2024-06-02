package com.example.first.api.controller;

import com.example.first.PropertyExample;
import com.example.first.service.ProductDto;
import com.example.first.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;
    private final PropertyExample propertyExample;

    @Value("${my.name}")
    private String name;

//    @Value("${my.list}")
//    private List<String> list;

    public ProductController(ProductService productService,
                             PropertyExample propertyExample) {
        this.productService = productService;
        this.propertyExample = propertyExample;
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
        propertyExample.getGoogle();
        productService.createPants(new ProductDto("pants", "pants"));
    }

}
