package com.example.projectvoucher.controller;

import com.example.projectvoucher.common.VoucherAmount;
import com.example.projectvoucher.response.VoucherPublishResponse;
import com.example.projectvoucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/vouchers")
@RestController
public class VoucherController {

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @PostMapping
  public ResponseEntity<VoucherPublishResponse> publishVoucher(@RequestParam VoucherAmount amount) {
    return ResponseEntity.ok(voucherService.publish(amount));
  }

  @PostMapping("/uses")
  public void useVoucher(@RequestParam String voucherCode) {
    voucherService.usingVoucher(voucherCode);
  }

  @PostMapping("/disable")
  public void disableVoucher(@RequestParam String voucherCode) {
    voucherService.disable(voucherCode);
  }

}
