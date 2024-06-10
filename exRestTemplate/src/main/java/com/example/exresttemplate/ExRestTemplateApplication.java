package com.example.exresttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExRestTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExRestTemplateApplication.class, args);
    }

}
