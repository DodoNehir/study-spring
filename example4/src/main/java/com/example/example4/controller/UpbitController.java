package com.example.example4.controller;

import com.example.example4.model.MinuteCandle;
import com.example.example4.model.MinuteCandleRequest;
import com.example.example4.service.UpbitMinuteCandleService;
import com.example.example4.validator.MinuteCandleValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UpbitController {
    private final MinuteCandleValidator minuteCandleValidator;
    private final UpbitMinuteCandleService upbitMinuteCandleService;

    public UpbitController(MinuteCandleValidator minuteCandleValidator, UpbitMinuteCandleService upbitMinuteCandleService) {
        this.minuteCandleValidator = minuteCandleValidator;
        this.upbitMinuteCandleService = upbitMinuteCandleService;
    }

    @GetMapping("api/v1/candles/minutes/{unit}")
    public List<MinuteCandle> getMinuteCandle(
            @PathVariable int unit,
            @RequestBody MinuteCandleRequest request,
            BindingResult bindingResult
    ) throws JsonProcessingException {

        minuteCandleValidator.validate(unit, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ArrayList<>();
        }

        return upbitMinuteCandleService.getCandles(unit, request);

    }
}
