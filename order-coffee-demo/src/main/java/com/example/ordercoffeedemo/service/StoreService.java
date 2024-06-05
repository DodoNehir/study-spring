package com.example.ordercoffeedemo.service;

import com.example.ordercoffeedemo.domain.StoreProduct;
import com.example.ordercoffeedemo.repository.StoreProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService {
    private final StoreProductRepository storeProductRepository;

    public StoreService(StoreProductRepository storeProductRepository) {
        this.storeProductRepository = storeProductRepository;
    }

    public StoreProduct getStoreProduct(int storeId, int productId) {
        Optional<StoreProduct> storeProductOptional = storeProductRepository.findByStoreIdAndProductId(storeId, productId);
        if (storeProductOptional.isEmpty()) {
            throw new RuntimeException("존재하지 않습니다.");
        }

        return storeProductOptional.get();
    }
}
