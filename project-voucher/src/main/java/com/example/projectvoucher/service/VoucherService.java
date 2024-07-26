package com.example.projectvoucher.service;

import com.example.projectvoucher.common.VoucherAmount;
import com.example.projectvoucher.entity.VoucherEntity;
import com.example.projectvoucher.repository.VoucherRepository;
import com.example.projectvoucher.response.VoucherPublishResponse;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  // 상품권 생성
  public VoucherPublishResponse publish(VoucherAmount amount) {
    VoucherEntity voucherEntity = VoucherEntity.of(amount);
    voucherRepository.save(voucherEntity);
    return VoucherPublishResponse.of(voucherEntity.getVoucherCode());
  }

  // 취소
  public String disable(String code) {
    VoucherEntity voucherEntity = voucherRepository.findByVoucherCode(code)
        .orElseThrow(() -> new RuntimeException("Voucher is not exists"));

    voucherEntity.disable();
    voucherRepository.save(voucherEntity);
    return voucherEntity.getVoucherCode();
  }

  // 사용
  public void usingVoucher(String code) {
    VoucherEntity voucherEntity = voucherRepository.findByVoucherCode(code)
        .orElseThrow(() -> new RuntimeException("Voucher is not exists"));

    voucherEntity.use();
    voucherRepository.save(voucherEntity);
  }

}
