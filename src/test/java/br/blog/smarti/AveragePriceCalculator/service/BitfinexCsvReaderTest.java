package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.service.BitfinexCsvReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;


public class BitfinexCsvReaderTest {

    private final String FILES_PATH = this.getClass().getClassLoader().getResource("").getPath();

    BitfinexCsvReader fixture = new BitfinexCsvReader();

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(fixture, "fileExtension", ".csv");
        ReflectionTestUtils.setField(fixture, "filesPath", FILES_PATH);
    }

    @Test
    void shouldReadBitfinexCsv() {
        List<BitfinexTrade> trades = fixture.readAllTradeFiles();
        Assertions.assertThat(trades).isNotEmpty().hasSize(52);
        Assertions.assertThat(trades.get(0).getSource()).isEqualToIgnoringCase("bitfinex_01.csv");
        Assertions.assertThat(trades.get(51).getSource()).isEqualToIgnoringCase("bitfinex_02.csv");

        int wrongExtensionSources = trades.stream().map(Trade::getSource).filter(s -> s.equalsIgnoreCase("wrong_extension.txt")).collect(Collectors.toList()).size();
        Assertions.assertThat(wrongExtensionSources).isEqualTo(0);
    }
}
