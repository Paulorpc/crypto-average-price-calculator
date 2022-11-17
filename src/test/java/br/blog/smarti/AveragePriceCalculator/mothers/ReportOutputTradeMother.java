package br.blog.smarti.AveragePriceCalculator.mothers;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReportOutputTradeMother {
    
    private ReportOutputTradeMother() {
        throw new UnsupportedOperationException("utility class");
    }
    
    public static List<ReportOutputTrade> createTradeList() {
        return Arrays.asList(
                createBuyTrade("Binance", "1234.56"), 
                createSellTrade("Binance", "456.789"), 
                createBuyTrade("Bitfinex", "321.456"),
                createSellTrade("Bitfinex", "465.777")
        );
    }
    
    public static ReportOutputTrade createBuyTrade() {
        ReportOutputTrade trade = new ReportOutputTrade();
        trade.setPrice(new BigDecimal("4000.0015072000"));
        trade.setAmount(new BigDecimal("0.00209501"));
        trade.setDateTime(LocalDateTime.now());
        trade.setExchange("binance");
        trade.setExecuted(new BigDecimal("1.3900000000"));
        trade.setFee("0.0001817000BNB");
        trade.setPair("BTCUSD");
        trade.setSide(SideEnum.BUY);
        trade.setSource("fileName");
        return trade;
    }

    public static ReportOutputTrade createSellTrade() {
        ReportOutputTrade trade = new ReportOutputTrade();
        trade.setPrice(new BigDecimal("3000.0015072000"));
        trade.setDateTime(LocalDateTime.now());
        trade.setSide(SideEnum.SELL);
        return trade;
    }

    public static ReportOutputTrade createBuyTrade(String exchange, String price) {
        ReportOutputTrade trade = createBuyTrade();
        trade.setPrice(new BigDecimal(price));
        trade.setExchange(exchange);
        return trade;
    }

    public static ReportOutputTrade createSellTrade(String exchange, String price) {
        ReportOutputTrade trade = createSellTrade();
        trade.setPrice(new BigDecimal(price));
        trade.setExchange(exchange);
        return trade;
    }
}
