package com.example.projectvoucher.response;

public record VoucherPublishResponse(
    String code
) {
  public static VoucherPublishResponse of(String code) {
    return new VoucherPublishResponse(code);
  }
}
