package com.example.projectvoucher.request;

import com.example.projectvoucher.common.VoucherAmount;

public record VoucherPublishRequest(
    VoucherAmount voucherAmount
) {

}
