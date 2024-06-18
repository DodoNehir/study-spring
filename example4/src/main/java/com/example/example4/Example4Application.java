package com.example.example4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Example4Application {

    public static void main(String[] args) {
        SpringApplication.run(Example4Application.class, args);
//        ConfigurableApplicationContext context = SpringApplication.run(Example4Application.class, args);
//        SpringApplication.exit(context);
    }

}
