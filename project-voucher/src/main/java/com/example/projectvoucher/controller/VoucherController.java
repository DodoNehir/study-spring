package com.example.projectvoucher.controller;

import com.example.projectvoucher.request.VoucherDisableRequest;
import com.example.projectvoucher.request.VoucherPublishRequest;
import com.example.projectvoucher.request.VoucherUseRequest;
import com.example.projectvoucher.response.VoucherDisableResponse;
import com.example.projectvoucher.response.VoucherPublishResponse;
import com.example.projectvoucher.response.VoucherUseResponse;
import com.example.projectvoucher.service.VoucherService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v2/vouchers")
@RestController
public class VoucherController {

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @PostMapping
  public ResponseEntity<VoucherPublishResponse> publishVoucher(
      @RequestBody VoucherPublishRequest request) {
    // todo orderId 임시
    String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    return ResponseEntity.ok(
        voucherService.publish(request.requester(), orderId, request.voucherAmount()));
  }

  @PostMapping("/uses")
  public VoucherUseResponse useVoucher(@RequestBody VoucherUseRequest request) {
    // todo orderId 임시
    String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    voucherService.usingVoucher(request.requester(), request.requestId(), request.voucherCode());
    return new VoucherUseResponse(orderId);
  }

  @PostMapping("/disable")
  public VoucherDisableResponse disableVoucher(@RequestBody VoucherDisableRequest request) {
    // todo orderId 임시
    String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    voucherService.disable(request.requester(), request.requestId(), request.voucherCode());
    return new VoucherDisableResponse(orderId);
  }

}
