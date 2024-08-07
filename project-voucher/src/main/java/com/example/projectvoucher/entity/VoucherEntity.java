package com.example.projectvoucher.entity;

import com.example.projectvoucher.common.VoucherAmount;
import com.example.projectvoucher.common.VoucherStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "voucher")
@Entity
public class VoucherEntity extends BaseEntity {

  private String voucherCode;
  private VoucherAmount amount;
  private VoucherStatus status;
  private LocalDate validFrom;
  private LocalDate validTo;
  private String usedUser;

  public VoucherEntity() {

  }

  public VoucherEntity(String voucherCode, VoucherAmount amount, VoucherStatus status,
      LocalDate validFrom, LocalDate validTo, String usedUser) {
    this.voucherCode = voucherCode;
    this.amount = amount;
    this.status = status;
    this.validFrom = validFrom;
    this.validTo = validTo;
    this.usedUser = usedUser;
  }

  public static VoucherEntity of(VoucherAmount amount) {
    VoucherEntity voucherEntity = new VoucherEntity();
    voucherEntity.setAmount(amount);
    return voucherEntity;
  }

  @PrePersist
  public void prePersist() {
    this.voucherCode = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    this.status = VoucherStatus.PUBLISHED;
    this.validFrom = LocalDate.now();
    this.validTo = validFrom.plusDays(1830);
    this.usedUser = " ";
  }

  public void disable() {
    this.status = VoucherStatus.DISABLED;
  }

  public void use() {
    if (this.status.equals(VoucherStatus.PUBLISHED)) {
      this.status = VoucherStatus.USED;
    } else {
      throw new IllegalStateException("Voucher status is " + this.status);
    }
  }
}
