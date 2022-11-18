package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.AveragePriceCalculator.mothers.BinanceTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.BitfinexTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.ReportOutputTradeMother;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportTradeServiceTest {

    private final String FILE_PATH = this.getClass().getClassLoader().getResource("").getPath();
    private final String FILE_NAME = "reportOutputTest.csv";
    @Mock
    BinanceCsvReader binanceCsvReader;
    @Mock
    BitfinexCsvReader bitfinexCsvReader;
    @InjectMocks
    ReportTradeService fixture = new ReportTradeService();

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(fixture, "filePath", FILE_PATH);
        ReflectionTestUtils.setField(fixture, "fileName", FILE_NAME);
    }

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
        fixture.generateReportOutputTradeCsv(ReportOutputTradeMother.createTradeList());

        File file = new File(FILE_PATH.concat(FILE_NAME));
        String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);

        Assertions.assertThat(fileContent).containsIgnoringCase("binance");
        Assertions.assertThat(fileContent).containsIgnoringCase("bitfinex");
    }
}

