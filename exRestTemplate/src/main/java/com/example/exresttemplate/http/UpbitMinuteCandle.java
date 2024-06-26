package com.example.exresttemplate.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpbitMinuteCandle {

    private String market;

    @JsonProperty("candle_date_time_utc")
    private String candleDateTimeUtc;

    @JsonProperty("candle_date_time_kst")
    private String candleDateTimeKst;

    @JsonProperty("opening_price")
    private double openingPrice;

    @JsonProperty("high_price")
    private double highPrice;

    @JsonProperty("low_price")
    private double lowPrice;

    @JsonProperty("trade_price")
    private double tradePrice;

    private long timestamp;

    @JsonProperty("candle_acc_trade_price")
    private double candleAccTradePrice;

    @JsonProperty("candle_acc_trade_volume")
    private double candleAccTradeVolume;

    private int unit;

}
