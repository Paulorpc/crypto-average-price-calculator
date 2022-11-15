package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.service.BinanceCsvReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class BinanceCsvReaderTest {
    
    BinanceCsvReader fixture = new BinanceCsvReader();

    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(fixture, "fileExtension", ".csv");
        ReflectionTestUtils.setField(fixture, "filesPath", "c:\\");
    }

    @Test
    void shouldReadBinanceCsv() {
        List<BinanceTrade> trades = fixture.readAllTradeFiles();
        Assertions.assertThat(trades)
                .isNotEmpty()
                .hasSize(331);
    }
}
