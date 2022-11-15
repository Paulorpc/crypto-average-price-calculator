package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.AveragePriceCalculator.mothers.BinanceTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.BitfinexTradeMother;
import br.blog.smarti.processor.service.BinanceCsvReader;
import br.blog.smarti.processor.service.BitfinexCsvReader;
import br.blog.smarti.processor.service.ReportTradeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportTradeServiceTest {

    @Mock
    BinanceCsvReader binanceCsvReader;

    @Mock
    BitfinexCsvReader bitfinexCsvReader;
    
    @InjectMocks
    ReportTradeService fixture = new ReportTradeService();
    
    @Test
    void shouldGenerateReportOutputTradeContent() {
        when(binanceCsvReader.readTrades()).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(bitfinexCsvReader.readTrades()).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));
        
        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent();
        
        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(4);
    }
}
