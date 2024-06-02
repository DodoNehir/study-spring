package com.example.first.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public Service testService() {
        return new TestService();
    }

    @Bean
//    @Primary
    public Service sampleService() {
        return new SampleService();
    }

}
