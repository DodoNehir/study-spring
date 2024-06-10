package com.example.exresttemplate.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="minute-candle", url = "https://api.upbit.com/")
public interface UpbitFeignClient {
    @GetMapping("/v1/candle/minutes/{unit}")
    String getMinuteCandle(
            @PathVariable int unit,
            @RequestParam String market,
            @RequestParam int count
    );
}
