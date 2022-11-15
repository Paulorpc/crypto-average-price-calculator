package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.service.BinanceCsvReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BinanceCsvReaderTest {
    
    BinanceCsvReader fixture = new BinanceCsvReader();
    
    @Test
    void shouldReadBinanceCsv() {
        List<BinanceTrade> trades = fixture.readTrades();
        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(331);
    }
}
