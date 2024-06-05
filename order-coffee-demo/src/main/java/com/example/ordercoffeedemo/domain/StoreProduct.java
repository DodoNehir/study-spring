package com.example.ordercoffeedemo.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "store_products")
public class StoreProduct {
    @Id
    private int storeProductId;

    @Column
    private String storeId;

    @Column
    private String productId;

    @Column
    private int stockQuantity;
}
