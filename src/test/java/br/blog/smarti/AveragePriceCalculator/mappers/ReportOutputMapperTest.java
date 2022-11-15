package br.blog.smarti.AveragePriceCalculator.mappers;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.enums.SideEnum;
import br.blog.smarti.AveragePriceCalculator.mothers.BinanceTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.BitfinexTradeMother;
import br.blog.smarti.processor.mappers.ReportOutputMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportOutputMapperTest {
    private ReportOutputMapper mapper = new ReportOutputMapper();
    
    @Test
    void mapFromBinanceTrade() {
        ReportOutputTrade outputTrade = mapper.toEntity(BinanceTradeMother.createBuyTrade());
        Assertions.assertEquals("4000.0015072000", outputTrade.getPrice().toString());
        Assertions.assertEquals("1.3900000000", outputTrade.getExecuted().toString());
        Assertions.assertEquals("0.00209501", outputTrade.getAmount().toString());
        Assertions.assertEquals("0.0001817000BNB", outputTrade.getFee().toString());
        Assertions.assertEquals("Binance", outputTrade.getExchange());
        Assertions.assertEquals("BTCUSD", outputTrade.getPair());
        Assertions.assertEquals("fileName", outputTrade.getSource());
        Assertions.assertEquals("BUY", outputTrade.getSide().toString());

    }

    @Test
    void mapFromBitfinexSellTrade() {
        ReportOutputTrade outputTrade = mapper.toEntity(BitfinexTradeMother.createSellTrade());
        Assertions.assertEquals("60000.00001197", outputTrade.getPrice().toString());
        Assertions.assertEquals("83.51464", outputTrade.getExecuted().toString());
        Assertions.assertEquals("5010878.4009996702408", outputTrade.getAmount().toString());
        Assertions.assertEquals("0.0000019993404816", outputTrade.getFee().toString());
        Assertions.assertEquals("Bitfinex", outputTrade.getExchange());
        Assertions.assertEquals("XLMBTC", outputTrade.getPair());
        Assertions.assertEquals("fileName", outputTrade.getSource());
        Assertions.assertEquals(SideEnum.SELL, outputTrade.getSide());

    }

    @Test
    void mapFromBitfinexBuyTrade() {
        ReportOutputTrade outputTrade = mapper.toEntity(BitfinexTradeMother.createBuyTrade());
        Assertions.assertEquals("50000.00001197", outputTrade.getPrice().toString());
        Assertions.assertEquals("83.51464", outputTrade.getExecuted().toString());
        Assertions.assertEquals("4175732.0009996702408", outputTrade.getAmount().toString());
        Assertions.assertEquals("0.0000019993404816", outputTrade.getFee().toString());
        Assertions.assertEquals("Bitfinex", outputTrade.getExchange());
        Assertions.assertEquals("XLMBTC", outputTrade.getPair());
        Assertions.assertEquals("fileName", outputTrade.getSource());
        Assertions.assertEquals(SideEnum.BUY, outputTrade.getSide());
    }
}
