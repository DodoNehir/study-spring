package com.example.exresttemplate.controller;

import com.example.exresttemplate.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/api/v1/test")
    public String HelloWorld() {
        return testService.getTest();
    }
}
