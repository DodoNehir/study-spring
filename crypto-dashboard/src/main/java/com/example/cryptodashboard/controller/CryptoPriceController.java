package com.example.cryptodashboard.controller;

import com.example.cryptodashboard.model.crypto.CryptoPriceResponse;
import com.example.cryptodashboard.model.currency.CurrencyResponse;
import com.example.cryptodashboard.model.price.PriceResponse;
import com.example.cryptodashboard.service.CurrencyMonitorService;
import com.example.cryptodashboard.service.PriceMonitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crypto-prices")
public class CryptoPriceController {

  private final PriceMonitorService priceMonitorService;
  private final CurrencyMonitorService currencyMonitorService;

  public CryptoPriceController(PriceMonitorService priceMonitorService,
      CurrencyMonitorService currencyMonitorService) {
    this.priceMonitorService = priceMonitorService;
    this.currencyMonitorService = currencyMonitorService;
  }

  @GetMapping("/{cryptoUnit}")
  public ResponseEntity<CryptoPriceResponse> getCryptoPrice(@PathVariable String cryptoUnit) {
    PriceResponse price = priceMonitorService.getPrice(cryptoUnit);
    CurrencyResponse currency = currencyMonitorService.getCurrency("USD");

    CryptoPriceResponse response = new CryptoPriceResponse(
        cryptoUnit, price.amount() * currency.rate()
    );

    return ResponseEntity.ok(response);
  }
}
