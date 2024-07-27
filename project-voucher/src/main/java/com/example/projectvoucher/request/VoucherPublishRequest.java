package com.example.projectvoucher.request;

import com.example.projectvoucher.common.Requester;
import com.example.projectvoucher.common.VoucherAmount;

public record VoucherPublishRequest(
    Requester requester,
    String requestId, // 쇼핑몰의 주문번호
    VoucherAmount voucherAmount
) {

}
