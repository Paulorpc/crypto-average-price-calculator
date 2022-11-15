package br.blog.smarti.AveragePriceCalculator.mothers;

import br.blog.smarti.processor.entity.BinanceTrade;

public class BinanceTradeMother {
    
    private BinanceTradeMother() {
        throw new UnsupportedOperationException("utility class");
    }

    public static BinanceTrade createBuyTrade() {
        BinanceTrade trade = new BinanceTrade();
        trade.setPrice("4,000.0015072000");
        trade.setAmount("0.00209501BTC");
        trade.setDateTime("2022-05-07 20:11:57");
        trade.setExchange("binance");
        trade.setExecuted("1.3900000000USD");
        trade.setFee("0.0001817000BNB");
        trade.setPair("BTCUSD");
        trade.setSide("BUY");
        trade.setSource("fileName");
        return trade;
    }

    public static BinanceTrade createSellTrade() {
        BinanceTrade trade = createBuyTrade();
        trade.setPrice("3,000.0015072000");
        trade.setDateTime("2022-05-08 20:11:57");
        trade.setSide("SELL");
        return trade;
    }
}
