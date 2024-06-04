package com.example.jdbcpractice.controller;

import com.example.jdbcpractice.entity.Test;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    @GetMapping("/api/v1/test/{testId}")
    public String getTest1(@PathVariable Integer testId) {
        return "getTest1";
    }

    @GetMapping("/api/v2/test/{testId}")
    public String getTest2(@PathVariable Integer testId,
                           @RequestParam String name) {
        return "getTest2";
    }

    @PostMapping("/api/v3/test/{testId}")
    public String postTest(
            @PathVariable Integer testId,
            @RequestParam String name,
            @RequestBody Test test
    ) {
        return "postTest";
    }

    @GetMapping("/api/v4/test/{testId}")
    public String getTest4(@PathVariable Integer testId,
                           @RequestParam List<String> names) {
        return "getTest4";
    }
}
