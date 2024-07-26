package com.example.projectvoucher.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.projectvoucher.common.VoucherAmount;
import com.example.projectvoucher.common.VoucherStatus;
import com.example.projectvoucher.entity.VoucherEntity;
import com.example.projectvoucher.repository.VoucherRepository;
import com.example.projectvoucher.response.VoucherPublishResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
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
    VoucherAmount amount10 = VoucherAmount.KRW_100000;
    VoucherPublishResponse published = voucherService.publish(amount10);

    // when
    Optional<VoucherEntity> optionalVoucherEntity = voucherRepository.findByVoucherCode(published.code());

    // then
    assertThat(published.code()).isNotNull();
    assertThat(optionalVoucherEntity.isPresent()).isTrue();
    assertThat(optionalVoucherEntity.get().getAmount()).isEqualTo(amount10);
  }

  @DisplayName("발행된 상품권을 취소할 수 있다.")
  @Test
  void disable() {
    // given
    VoucherAmount amount5 = VoucherAmount.KRW_50000;
    VoucherPublishResponse published = voucherService.publish(amount5);

    // when
    voucherService.disable(published.code());
    Optional<VoucherEntity> optionalVoucherEntity = voucherRepository.findByVoucherCode(published.code());
    VoucherEntity entity = optionalVoucherEntity.get();

    // then
    assertThat(entity.getStatus()).isEqualTo(VoucherStatus.DISABLED);
    assertThat(entity.getCreatedAt()).isNotEqualTo(entity.getUpdatedAt());
  }

  @DisplayName("사용한 상품권은 다시 사용 불가")
  @Test
  void reuse() {
    // given
    VoucherAmount amount3 = VoucherAmount.KRW_30000;
    VoucherPublishResponse published = voucherService.publish(amount3);

    // when
    voucherService.usingVoucher(published.code());
    Optional<VoucherEntity> optionalVoucherEntity = voucherRepository.findByVoucherCode(published.code());
    VoucherEntity entity = optionalVoucherEntity.get();

    // then
    assertThat(entity.getStatus()).isEqualTo(VoucherStatus.USED);
    assertThatThrownBy(() -> voucherService.usingVoucher(published.code()))
        .isInstanceOf(IllegalStateException.class);

  }
}
