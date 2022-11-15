package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.service.BitfinexCsvReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BitfinexCsvReaderTest {

    BitfinexCsvReader fixture = new BitfinexCsvReader();

    @Test
    void shouldReadBitfinexCsv() {
        List<BitfinexTrade> trades = fixture.readTrades();
        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(192);
    }
}
