package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.service.BinanceCsvReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceCsvReaderTest {

    private final String FILES_PATH = this.getClass().getClassLoader().getResource("").getPath();

    BinanceCsvReader fixture = new BinanceCsvReader();

    @BeforeEach
    public void setup() throws FileNotFoundException {
        ReflectionTestUtils.setField(fixture, "fileExtension", ".csv");
        ReflectionTestUtils.setField(fixture, "filesPath", FILES_PATH);
    }

    @Test
    void shouldReadBinanceCsv() throws FileNotFoundException {
        List<BinanceTrade> trades = fixture.readAllTradeFiles();
        Assertions.assertThat(trades).isNotEmpty().hasSize(140);
        Assertions.assertThat(trades.get(0).getSource()).isEqualToIgnoringCase("binance_01.csv");
        Assertions.assertThat(trades.get(139).getSource()).isEqualToIgnoringCase("binance_02.csv");

        int wrongExtensionSources = trades.stream().map(Trade::getSource).filter(s -> s.equalsIgnoreCase("wrong_extension.txt")).collect(Collectors.toList()).size();
        Assertions.assertThat(wrongExtensionSources).isEqualTo(0);
    }

    @Test
    void shouldNotReadCsvFilesNotFound() throws FileNotFoundException {
        Assertions.assertThatThrownBy(() -> fixture.readAllTradeFiles("c:\\reports"))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessageContaining("Your trade report name must begin with 'binance'. Ex: 'binance_trades_jan-dec_2021.csv'");
    }
}
