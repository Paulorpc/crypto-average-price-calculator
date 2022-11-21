package br.blog.smarti.AveragePriceCalculator.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.service.BinanceCsvReaderService;
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
public class BinanceCsvReaderServiceTest {
    @Mock
    private FileUtils fileUtils;
    @InjectMocks
    private BinanceCsvReaderService fixture;

    @Test
    void shouldReadBinanceCsv() throws FileNotFoundException {
        File filePath = new File(this.getClass().getClassLoader().getResource("").getPath());
        File file01 = new File(getClass().getClassLoader().getResource("binance_01.csv").getFile());
        File file02 = new File(getClass().getClassLoader().getResource("binance_02.csv").getFile());
        
        when(fileUtils.getInputFolder(any())).thenReturn(filePath);
        when(fileUtils.listFilesFromFolder(any(), any())).thenReturn(List.of(file01, file02));

        List<BinanceTrade> trades = fixture.readAllTradeFiles();

        Assertions.assertThat(trades).isNotEmpty().hasSize(140);
        Assertions.assertThat(trades.get(0).getSource()).isEqualToIgnoringCase("binance_01.csv");
        Assertions.assertThat(trades.get(139).getSource()).isEqualToIgnoringCase("binance_02.csv");
        int wrongExtensionSources = trades.stream().map(Trade::getSource).filter(s -> s.equalsIgnoreCase("wrong_extension.txt")).collect(Collectors.toList()).size();
        Assertions.assertThat(wrongExtensionSources).isEqualTo(0);
    }
}
