package model.exchange;

public record ExchangeResponse(
    String cur_unit, // 통화코드 (USD)
    String cur_nm, // 통화명 (미국 달러)
    String deal_bas_r // 매매 기준율
) {

}
