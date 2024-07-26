package com.example.projectvoucher.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.projectvoucher.entity.VoucherEntity;
import com.example.projectvoucher.repository.VoucherRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherServiceTest {

  @Autowired
  private VoucherService voucherService;
  @Autowired
  private VoucherRepository voucherRepository;

  @Test
  void publish() {
    // given
    Long amount3 = 30000L;
    Long amount5 = 50000L;
    Long amount10 = 100000L;
    String code = voucherService.publish(amount3);

    // when
    Optional<VoucherEntity> optionalVoucherEntity = voucherRepository.findByVoucherCode(code);

    // then
    assertThat(code).isNotNull();
    assertThat(optionalVoucherEntity.isPresent()).isTrue();
    assertThat(optionalVoucherEntity.get().getAmount()).isEqualTo(amount3);
  }
}