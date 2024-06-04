package com.example.jdbcpractice.service;

import com.example.jdbcpractice.entity.Test;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TestService {
    public String test(@Valid Test test) {
        return "test service";
    }
}
