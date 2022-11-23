package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.AveragePriceCalculator.mothers.BinanceTradeMother;
import br.blog.smarti.AveragePriceCalculator.mothers.BitfinexTradeMother;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.mappers.ReportOutputMapper;
import br.blog.smarti.processor.service.BinanceCsvReaderService;
import br.blog.smarti.processor.service.BitfinexCsvReaderService;
import br.blog.smarti.processor.service.CsvTradesReader;
import br.blog.smarti.processor.service.ReportTradeService;
import br.blog.smarti.processor.utils.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportTradeServiceTest {
    @InjectMocks
    ReportTradeService fixture;

    @Mock
    BinanceCsvReaderService binanceCsvReader;

    @Mock
    BitfinexCsvReaderService bitfinexCsvReader;

    @Spy
    List<CsvTradesReader> csvTradesReader;

    @Mock
    private FileUtils fileUtils;

    @Spy
    private ReportOutputMapper reportOutputMapper = Mappers.getMapper(ReportOutputMapper.class);

    @Test
    void shouldGenerateReportOutputTradeContentFromBinance() throws FileNotFoundException {
        when(csvTradesReader.stream()).thenReturn(Stream.of(getCsvReaders()));
        when(binanceCsvReader.readAllTradeFiles()).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(reportOutputMapper.toEntity(any(Trade.class))).thenReturn(new ReportOutputTrade());

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BINANCE);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(2);

        long sourceFiles = trades.stream().map(Trade::getSource).distinct().count();
        Assertions.assertThat(sourceFiles).isEqualTo(1);

        verify(binanceCsvReader, times(1)).readAllTradeFiles();
        verify(bitfinexCsvReader, times(0)).readAllTradeFiles();
    }

    @Test
    void shouldGenerateReportOutputTradeContentFromBitfinex() throws FileNotFoundException {
        when(csvTradesReader.stream()).thenReturn(Stream.of(getCsvReaders()));
        when(bitfinexCsvReader.readAllTradeFiles()).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));
        when(reportOutputMapper.toEntity(any(Trade.class))).thenReturn(new ReportOutputTrade());

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BITFINEX);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(2);

        long sourceFiles = trades.stream().map(Trade::getSource).distinct().count();
        Assertions.assertThat(sourceFiles).isEqualTo(1);

        verify(binanceCsvReader, times(0)).readAllTradeFiles();
        verify(bitfinexCsvReader, times(1)).readAllTradeFiles();
    }

    @Test
    void shouldGenerateReportOutputTradeContentFrom2ExchangeFiles() throws FileNotFoundException {
        var bitfinexBuyTrade = BitfinexTradeMother.createBuyTrade();
        bitfinexBuyTrade.setSource("someOtherFile.csv");

        when(csvTradesReader.stream()).thenReturn(Stream.of(getCsvReaders()));
        when(bitfinexCsvReader.readAllTradeFiles()).thenReturn(List.of(bitfinexBuyTrade, BitfinexTradeMother.createSellTrade()));

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BITFINEX);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(2);

        long sourceFiles = trades.stream().map(Trade::getSource).distinct().count();
        Assertions.assertThat(sourceFiles).isEqualTo(2);

        verify(binanceCsvReader, times(0)).readAllTradeFiles();
        verify(bitfinexCsvReader, times(1)).readAllTradeFiles();
    }

    @Test
    void shouldGenerateReportOutputTradeContentFromAllExchanges() throws FileNotFoundException {
        when(csvTradesReader.stream()).thenReturn(Stream.of(getCsvReaders()));
        when(binanceCsvReader.readAllTradeFiles()).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(bitfinexCsvReader.readAllTradeFiles()).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));
        when(reportOutputMapper.toEntity(any(Trade.class))).thenReturn(new ReportOutputTrade());

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent();

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(4);

        verify(binanceCsvReader, times(1)).readAllTradeFiles();
        verify(bitfinexCsvReader, times(1)).readAllTradeFiles();
    }

    @Test
    void shouldGenerateReportOutputTradeContentFromAllExchangesWithParameter() throws FileNotFoundException {
        when(csvTradesReader.stream()).thenAnswer(getStreamAnswer(getCsvReaders()));
        when(binanceCsvReader.readAllTradeFiles()).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(bitfinexCsvReader.readAllTradeFiles()).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));
        when(reportOutputMapper.toEntity(any(Trade.class))).thenReturn(new ReportOutputTrade());

        List<ReportOutputTrade> trades = fixture.generateReportOutputTradeContent(ExchangesEnum.BINANCE, ExchangesEnum.BITFINEX);

        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(4);

        verify(binanceCsvReader, times(1)).readAllTradeFiles();
        verify(bitfinexCsvReader, times(1)).readAllTradeFiles();
    }

    @Test
    void shouldGenerateReportOutputTradeCsvFile() throws IOException {
        String fileName = "reportOutputTest.csv";
        String pathName = getClass().getClassLoader().getResource("").toString()
                .replace("file:", "").concat(fileName);

        when(fileUtils.getOutputFilePathName()).thenReturn(new File(pathName));
        when(csvTradesReader.stream()).thenAnswer(getStreamAnswer(getCsvReaders()));
        when(binanceCsvReader.readAllTradeFiles()).thenReturn(List.of(BinanceTradeMother.createBuyTrade(), BinanceTradeMother.createSellTrade()));
        when(bitfinexCsvReader.readAllTradeFiles()).thenReturn(List.of(BitfinexTradeMother.createBuyTrade(), BitfinexTradeMother.createSellTrade()));

        fixture.generateReportOutputTradeCsv();

        File filePathName = new File(getClass().getClassLoader().getResource(fileName).getFile());
        String fileContent = Files.readString(filePathName.toPath(), StandardCharsets.UTF_8);
        Assertions.assertThat(fileContent).containsIgnoringCase("binance");
        Assertions.assertThat(fileContent).containsIgnoringCase("bitfinex");
    }

    private CsvTradesReader[] getCsvReaders() {
        return new CsvTradesReader[]{binanceCsvReader, bitfinexCsvReader};
    }

    /***
     * Necessário pois o stream soh pode ser acessado uma únca vez, então é preciso gerar novas instancias quando 
     * é percorrido mais de uma vez. 
     * @param readers
     * @return
     */
    private Answer<Stream> getStreamAnswer(CsvTradesReader... readers) {
        Answer<Stream> answer = new Answer<Stream>() {
            @Override
            public Stream answer(InvocationOnMock invocation) throws Throwable {
                return Stream.of(readers);
            }
        };
        return answer;
    }
}

