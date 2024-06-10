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
    // RestTemplate을 만들 때 RestTemplateBuilder를 주입받는다.
    // RestTemplateBuilder는 스프링부트를 사용하는 환경이면 바로 사용할 수 있다.
    // 자동으로 타임아웃이 지정되지 않기 때문에 직접 해줘야 한다.
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
