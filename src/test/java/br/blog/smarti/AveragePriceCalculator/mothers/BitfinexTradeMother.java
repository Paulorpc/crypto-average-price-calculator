package br.blog.smarti.AveragePriceCalculator.mothers;

import br.blog.smarti.processor.entity.BitfinexTrade;

public final class BitfinexTradeMother {
    
    private BitfinexTradeMother() {
        throw new UnsupportedOperationException("utility class");
    }
    
    public static BitfinexTrade createBuyTrade() {
        BitfinexTrade trade = new BitfinexTrade();
        trade.setPrice("5,0000.00001197");
        trade.setDateTime("2022-05-09 20:11:57");
        trade.setExchange("bitfinex");
        trade.setExecuted("83.51464");
        trade.setFee("0.0000019993404816");
        trade.setFeePerc("0.20%");
        trade.setPair("XLM/BTC");
        trade.setSource("fileName");
        return trade;
    }

    public static BitfinexTrade createSellTrade() {
        BitfinexTrade trade = createBuyTrade();
        trade.setPrice("6,0000.00001197");
        trade.setDateTime("2022-05-10 20:11:57");
        trade.setExecuted("-83.51464");
        trade.setFee("-0.0000019993404816");
        return trade;
    }

}
