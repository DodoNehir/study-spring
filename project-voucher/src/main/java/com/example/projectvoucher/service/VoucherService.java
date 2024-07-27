package com.example.projectvoucher.service;

import com.example.projectvoucher.common.Requester;
import com.example.projectvoucher.common.VoucherAmount;
import com.example.projectvoucher.entity.VoucherEntity;
import com.example.projectvoucher.repository.VoucherRepository;
import com.example.projectvoucher.response.VoucherPublishResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  // 상품권 생성
  @Transactional
  public VoucherPublishResponse publish(
      final Requester requester,
      final String requesterId,
      final VoucherAmount amount) {
    VoucherEntity voucherEntity = VoucherEntity.of(amount);
    voucherRepository.save(voucherEntity);
    return new VoucherPublishResponse(requesterId, voucherEntity.getVoucherCode());
  }

  // 사용
  @Transactional
  public void usingVoucher(
      final Requester requester,
      final String requesterId,
      final String code) {
    VoucherEntity voucherEntity = voucherRepository.findByVoucherCode(code)
        .orElseThrow(() -> new RuntimeException("Voucher is not exists"));

    voucherEntity.use();
    voucherRepository.save(voucherEntity);
  }

  // 취소
  @Transactional
  public String disable(
      final Requester requester,
      final String requesterId,
      final String code) {
    VoucherEntity voucherEntity = voucherRepository.findByVoucherCode(code)
        .orElseThrow(() -> new RuntimeException("Voucher is not exists"));

    voucherEntity.disable();
    voucherRepository.save(voucherEntity);
    return voucherEntity.getVoucherCode();
  }


}
