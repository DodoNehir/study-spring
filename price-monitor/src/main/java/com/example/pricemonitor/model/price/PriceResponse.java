package com.example.pricemonitor.model.price;

import com.example.pricemonitor.model.SpotPrice;

public record PriceResponse(
    Double amount,
    String base,
    String currency
) {

  public static PriceResponse from(SpotPrice spotPrice) {
    return new PriceResponse(
        Double.parseDouble(spotPrice.data().amount()),
        spotPrice.data().base(),
        spotPrice.data().currency()
    );
  }
}
