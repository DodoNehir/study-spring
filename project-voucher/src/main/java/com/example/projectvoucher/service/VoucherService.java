package com.example.projectvoucher.service;

import com.example.projectvoucher.entity.VoucherEntity;
import com.example.projectvoucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  // 상품권 생성
  public String publish(Long amount) {
    VoucherEntity voucherEntity = VoucherEntity.of(amount);
    return voucherRepository.save(voucherEntity).getVoucherCode();
  }

  // 취소

  // 사용

}
