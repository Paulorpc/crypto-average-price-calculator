package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.service.BitfinexCsvReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class BitfinexCsvReaderTest {

    BitfinexCsvReader fixture = new BitfinexCsvReader();

    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(fixture, "FILE_EXTENSION", ".csv");
        ReflectionTestUtils.setField(fixture, "FILES_PATH", "c:\\");
    }
    
    @Test
    void shouldReadBitfinexCsv() {
        List<BitfinexTrade> trades = fixture.readTrades();
        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(192);
    }
}
