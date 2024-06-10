package com.example.exresttemplate.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RestTemplateConfig {

    // RestTemplate 을 필요한 곳에서 주입받을 수 있도록
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.of(1, ChronoUnit.SECONDS))
                .setReadTimeout(Duration.of(5, ChronoUnit.SECONDS))
                .build();
        // connection 연결에 1초 이상 걸리면 에러
        // 읽어오는 데에 5초 이상 걸리면 에러
    }
}
