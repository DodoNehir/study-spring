package com.example.exresttemplate.http;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {
    private final RestTemplate restTemplate;

    public HttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getData(String uri, HttpMethod method, HttpHeaders headers) {
        return restTemplate.exchange(
                uri,
                method,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<String>() {}
        ).getBody();
    }
}
