package com.example.projectvoucher.entity;

import com.example.projectvoucher.common.VoucherStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "voucher")
@Entity
public class VoucherEntity extends BaseEntity {

  private String voucherCode;
  private Long amount;
  private VoucherStatus status;
  private LocalDate validFrom;
  private LocalDate validTo;
  private String usedUser;

  public VoucherEntity() {

  }

  public VoucherEntity(String voucherCode, Long amount, VoucherStatus status,
      LocalDate validFrom, LocalDate validTo, String usedUser) {
    this.voucherCode = voucherCode;
    this.amount = amount;
    this.status = status;
    this.validFrom = validFrom;
    this.validTo = validTo;
    this.usedUser = usedUser;
  }
}
