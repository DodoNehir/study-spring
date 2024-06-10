package com.example.exresttemplate.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MinuteCandle {
    // 내부용 데이터
    private String market;

    private String candleDateTimeUtc;

    private String candleDateTimeKst;

    private double openingPrice;

    private double highPrice;

    private double lowPrice;

    private double tradePrice;

    private long timestamp;

    private double candleAccTradePrice;

    private double candleAccTradeVolume;

    private int unit;
}
