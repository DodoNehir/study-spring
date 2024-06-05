package com.example.ordercoffeedemo.repository;

import com.example.ordercoffeedemo.domain.StoreProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StoreProductRepository extends CrudRepository<StoreProduct, Integer> {
    // 단순 조회나 저장이 아니라 특정 조건에 따라서 데이터를 가져올 필요가 있다.
    Optional<StoreProduct> findByStoreIdAndProductId(int store, int productId);
    // 특정 매장의 특정 상품에 대한 값을 가져올 필요가 있어서.
}
