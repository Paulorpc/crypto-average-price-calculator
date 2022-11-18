package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.AveragePriceCalculator.mothers.BinanceTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.BitfinexTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.ReportOutputTradeMother;
import br.blog.smarti.processor.Utils.FileUtils;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.service.BinanceCsvReaderService;
import br.blog.smarti.processor.service.BitfinexCsvReaderService;
import br.blog.smarti.processor.service.ReportTradeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportTradeServiceTest {
    @InjectMocks
    ReportTradeService fixture = new ReportTradeService();
    @Mock
    private BinanceCsvReaderService binanceCsvReader;
    @Mock
    private BitfinexCsvReaderService bitfinexCsvReader;
    @Mock
    private FileUtils fileUtils;

    @Test
    void shouldNotGenerateReportOutputTradeMissingParameter() {
        Assertions.assertThatThrownBy(() -> fixture.generateReportOutputTradeContent())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("you must indicate wich reports you want to process");
    }

    @Test
    void shouldGenerateReportOutputTradeContentFromBinance() throws FileNotFoundException {
        when(binanceCsvReader.readAllTradeFiles(any())).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BINANCE);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(2);

        verify(binanceCsvReader, times(1)).readAllTradeFiles(any());
        verify(bitfinexCsvReader, times(0)).readAllTradeFiles(any());
    }

    @Test
    void shouldGenerateReportOutputTradeContentFromBitfinex() throws FileNotFoundException {
        when(bitfinexCsvReader.readAllTradeFiles(any())).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BITFINEX);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(2);

        verify(binanceCsvReader, times(0)).readAllTradeFiles(any());
        verify(bitfinexCsvReader, times(1)).readAllTradeFiles(any());
    }

    @Test
    void shouldGenerateReportOutputTradeContentFromAllExchanges() throws FileNotFoundException {
        when(binanceCsvReader.readAllTradeFiles(any())).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(bitfinexCsvReader.readAllTradeFiles(any())).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BINANCE, ExchangesEnum.BITFINEX);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(4);

        verify(binanceCsvReader, times(1)).readAllTradeFiles(any());
        verify(bitfinexCsvReader, times(1)).readAllTradeFiles(any());
    }

    @Test
    void shouldGenerateReportOutputTradeCsvFile() throws IOException {
        String fileName = "reportOutputTest.csv";
        File filePathName = new File(this.getClass().getClassLoader().getResource(fileName).getPath());

        when(fileUtils.getOutputFileNamePath(any())).thenReturn(filePathName);

        fixture.generateReportOutputTradeCsv(ReportOutputTradeMother.createTradeList());

        String fileContent = Files.readString(filePathName.toPath(), StandardCharsets.UTF_8);
        Assertions.assertThat(fileContent).containsIgnoringCase("binance");
        Assertions.assertThat(fileContent).containsIgnoringCase("bitfinex");
    }
}

