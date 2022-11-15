package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Log4j
@Service
public class BitfinexCsvReader extends CsvReader {
    
    private static final String EXCHANGE_NAME = ExchangesEnum.BITFINEX.getName().toLowerCase();

    @Value("${suported.files.extension}")
    private static String FILE_EXTENSION;

    @Value("${files.input.path}")
    private static String FILES_PATH;

    public List<BitfinexTrade> readTrades() {
        File inputFolder = new File(FILES_PATH);
        FilenameFilter filter = (dir, name) -> name.toLowerCase().startsWith(EXCHANGE_NAME) && name.toLowerCase().endsWith(FILE_EXTENSION);
        List<File> reportsFiles = Arrays.asList(inputFolder.listFiles(filter));
        
        List<BitfinexTrade> trades = new ArrayList<>();
        reportsFiles.forEach(file -> {
            try {
                trades.addAll(this.readTuples(file, BitfinexTrade.class));
            }
            catch(Exception e) {
                log.error(format("Error reading file %s. %s", file, e.getMessage()));
            }
        });
        return trades;
    }
    
}
