package service;

import java.util.Arrays;
import model.exchange.ExchangeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExchangeService {

  private static final RestClient restClient = RestClient.create();

  private final String apiUri;
  private final String authKey;

  public ExchangeService(
      @Value("${kexim.api-uri}") String apiUri,
      @Value("${kexim.auth-key}") String authKey) {
    this.apiUri = apiUri;
    this.authKey = authKey;
  }

  public ExchangeResponse getExchangeByCurrency(String currency) {
    ExchangeResponse[] exchangeDatas = restClient
        .get()
        .uri(apiUri + authKey + "&searchdate=20240709&data=AP01")
        .retrieve()
        .body(ExchangeResponse[].class);

    if (exchangeDatas == null || exchangeDatas.length == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return Arrays.stream(exchangeDatas)
        .filter(exchangeData -> exchangeData.cur_unit().equals(currency))
        .findFirst()
        .orElse(new ExchangeResponse("USD", "미국 달러", "2000"));

  }

}
