package com.example.example4.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MinuteCandle {

    private String market; // 마켓명

    private String candleDateTimeUtc; // 캔들 기준 시각 (UTC 기준)

    private String candleDateTimeKst; // 캔들 기준 시각 (KST 기준)

    private double openingPrice; // 시가

    private double highPrice; // 고가

    private double lowPrice; // 저가

    private double tradePrice; // 종가

    private long timestamp; // 해당 캔들에서 마지막 틱이 저장된 시각

    private double candleAccTradePrice; // 누적 거래 금액

    private double candleAccTradeVolume; // 누적 거래량

    private int unit; // 분 단위 (유닛)
}
