package com.example.example4.service;

import com.example.example4.http.RestTemplateClient;
import com.example.example4.http.UpbitMinuteCandle;
import com.example.example4.model.MinuteCandle;
import com.example.example4.model.MinuteCandleRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UpbitMinuteCandleService {

    private final RestTemplateClient restTemplateClient;
    private final ObjectMapper objectMapper;

    public UpbitMinuteCandleService(RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
        this.objectMapper = new ObjectMapper();
    }

    public List<MinuteCandle> getCandles(int unit, MinuteCandleRequest request) throws JsonProcessingException {

        String uri = UriComponentsBuilder.fromUriString("https://api.upbit.com/")
                .path("v1/candles/minutes/" + unit)
                .queryParam("market", request.getMarket())
                .queryParam("count", request.getCount())
                .build()
                .toUriString();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/json");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headerMap);

        // call
        String candleData = restTemplateClient.getCandleData(uri, HttpMethod.GET, httpHeaders);

        List<UpbitMinuteCandle> upbitMinuteCandleList = objectMapper.readValue(
                candleData,
                new TypeReference<List<UpbitMinuteCandle>>() {
                });

        return upbitMinuteCandleList.stream()
                .map(it -> MinuteCandle.builder()
                        .market(it.getMarket())
                        .build())
                .collect(Collectors.toList());

    }
}
