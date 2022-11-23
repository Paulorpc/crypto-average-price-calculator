package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.service.BitfinexCsvReaderService;
import br.blog.smarti.processor.utils.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BitfinexCsvReaderServiceTest {
    @Mock
    private FileUtils fileUtils;
    @InjectMocks
    private BitfinexCsvReaderService fixture;

    @Test
    void shouldReadBitfinexCsv() throws FileNotFoundException {
        File filePath = new File(this.getClass().getClassLoader().getResource("").getPath());
        File file01 = new File(getClass().getClassLoader().getResource("bitfinex_01.csv").getFile());
        File file02 = new File(getClass().getClassLoader().getResource("bitfinex_02.csv").getFile());
        
        when(fileUtils.listFilesFromInputFolder(any())).thenReturn(List.of(file01, file02));

        List<BitfinexTrade> trades = fixture.readAllTradeFiles();

        Assertions.assertThat(trades).isNotEmpty().hasSize(52);
        Assertions.assertThat(trades.get(0).getSource()).isEqualToIgnoringCase("bitfinex_01.csv");
        Assertions.assertThat(trades.get(51).getSource()).isEqualToIgnoringCase("bitfinex_02.csv");
        int wrongExtensionSources = trades.stream().map(Trade::getSource).filter(s -> s.equalsIgnoreCase("wrong_extension.txt")).collect(Collectors.toList()).size();
        Assertions.assertThat(wrongExtensionSources).isEqualTo(0);
    }
}
