package com.example.example4.http;

import com.example.example4.exception.UpbitClientException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateClient {
    private final RestTemplate restTemplate;

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCandleData(String uri, HttpMethod method, HttpHeaders headers) {
        try {
            return restTemplate.exchange(
                    uri,
                    method,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<String>() {
                    }
            ).getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new UpbitClientException(e.getMessage());
        }
    }
}
