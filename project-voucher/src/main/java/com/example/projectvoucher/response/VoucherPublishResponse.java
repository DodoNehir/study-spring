package com.example.projectvoucher.response;

public record VoucherPublishResponse(
    String orderId, // 발주번호 (승인번호 같은 거)
    String code
) {
}
