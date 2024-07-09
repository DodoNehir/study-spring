package com.example.pricemonitor.service;

import com.example.pricemonitor.model.SpotPrice;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PriceService {

  private static final Logger logger = LoggerFactory.getLogger(PriceService.class);
  private static final RestClient restClient = RestClient.create();

  public SpotPrice getSpotPriceByCurrencyPair(String currencyPair) {
    SpotPrice response = restClient
        .get()
        .uri("https://api.coinbase.com/v2/prices/{currencyPair}/buy", currencyPair)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError,
            (request, response1) -> {
              logger.error(new String(response1.getBody().readAllBytes(), StandardCharsets.UTF_8));
            })
        .body(SpotPrice.class);

    logger.info(response.toString());

    return response;
  }

}
