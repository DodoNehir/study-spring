package com.example.example4.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpbitMinuteCandle {

    private String market; // 마켓명

    @JsonProperty("candle_date_time_utc")
    private String candleDateTimeUtc; // 캔들 기준 시각 (UTC 기준)

    @JsonProperty("candle_date_time_kst")
    private String candleDateTimeKst; // 캔들 기준 시각 (KST 기준)

    @JsonProperty("opening_price")
    private double openingPrice; // 시가

    @JsonProperty("high_price")
    private double highPrice; // 고가

    @JsonProperty("low_price")
    private double lowPrice; // 저가

    @JsonProperty("trade_price")
    private double tradePrice; // 종가

    private long timestamp; // 해당 캔들에서 마지막 틱이 저장된 시각

    @JsonProperty("candle_acc_trade_price")
    private double candleAccTradePrice; // 누적 거래 금액

    @JsonProperty("candle_acc_trade_volume")
    private double candleAccTradeVolume; // 누적 거래량

    private int unit; // 분 단위 (유닛)

}
