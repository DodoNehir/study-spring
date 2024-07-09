package controller;

import model.currency.CurrencyResponse;
import model.exchange.ExchangeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import service.ExchangeService;

@RestController("/api/v1/currencies")
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
