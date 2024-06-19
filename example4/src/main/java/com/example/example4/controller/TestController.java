package com.example.example4.controller;

import com.example.example4.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestService testService;

    TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/api/v1/test")
    public String test() {
        return testService.getTest();
    }
}
