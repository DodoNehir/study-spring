package com.example.pricemonitor.controller;

import com.example.pricemonitor.model.SpotPrice;
import com.example.pricemonitor.model.price.PriceResponse;
import com.example.pricemonitor.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/prices")
public class PriceController {

  private final PriceService priceService;

  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @GetMapping("/{currencyPair}/market-price")
  public ResponseEntity<PriceResponse> getMarketPrice(@PathVariable String currencyPair) {
    SpotPrice spotPriceByCurrencyPair = priceService.getSpotPriceByCurrencyPair(currencyPair);
    return ResponseEntity.ok(PriceResponse.from(spotPriceByCurrencyPair));
  }
}
