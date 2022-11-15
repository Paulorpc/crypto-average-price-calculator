package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.AveragePriceCalculator.mothers.ReportOutputTradeMother;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.AveragePriceCalculator.mothers.BinanceTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.BitfinexTradeMother;
import br.blog.smarti.processor.service.BinanceCsvReader;
import br.blog.smarti.processor.service.BitfinexCsvReader;
import br.blog.smarti.processor.service.ReportTradeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

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

    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(fixture, "fileOutputPath", "e:\\output.csv");
    }
    
    @Test
    void shouldGenerateReportOutputTradeContent() {
        when(binanceCsvReader.readAllTradeFiles()).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(bitfinexCsvReader.readAllTradeFiles()).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));
        
        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent();
        
        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(4);
    }

    @Test
    void shouldGenerateReportOutputTradeCsvFile() {
        List<ReportOutputTrade> trades = ReportOutputTradeMother.createTradeList();
        fixture.generateReportOutputTradeCsv(trades);
    }
}
