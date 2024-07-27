package com.example.projectvoucher.request;

import com.example.projectvoucher.common.Requester;

public record VoucherDisableRequest(
    Requester requester,
    String requestId, // 쇼핑몰의 주문번호
    String voucherCode
) {

}
