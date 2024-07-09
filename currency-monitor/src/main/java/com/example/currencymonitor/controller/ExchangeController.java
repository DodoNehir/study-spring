package com.example.currencymonitor.controller;


import com.example.currencymonitor.model.currency.CurrencyResponse;
import com.example.currencymonitor.model.exchange.ExchangeResponse;
import com.example.currencymonitor.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currencies")
public class ExchangeController {

  private final ExchangeService exchangeService;

  public ExchangeController(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  @GetMapping("/{currency}")
  public ResponseEntity<CurrencyResponse> getCurrency(@PathVariable String currency) {
    ExchangeResponse exchangeByCurrency = exchangeService.getExchangeByCurrency(currency);
    return ResponseEntity.ok(CurrencyResponse.from(exchangeByCurrency));
  }

}
