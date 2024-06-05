package com.example.ordercoffeedemo.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Table(name = "products")
public class Product {
    @Id
    private int productId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;
}
