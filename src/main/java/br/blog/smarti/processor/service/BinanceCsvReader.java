package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@Service
public class BinanceCsvReader extends CsvReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BINANCE.getName().toLowerCase();

    @Value("${supported.files.extension}")
    private String fileExtension;

    @Value("${files.input.path}")
    private String filesPath;
    
    public List<BinanceTrade> readAllTradeFiles() {
        File inputFolder = new File(filesPath);
        FilenameFilter filter = (dir, name) -> name.toLowerCase().startsWith(EXCHANGE_NAME) && name.toLowerCase().endsWith(fileExtension);
        List<File> reportsFiles = Arrays.asList(inputFolder.listFiles(filter));

        List<BinanceTrade> trades = new ArrayList<>();
        List<BinanceTrade> fileTrades = new ArrayList<>();

        for (File file : reportsFiles) {
            try {
                fileTrades.addAll(this.readTuples(file, BinanceTrade.class));
                fileTrades.stream().forEach(t -> t.setSource(file.getName()));
                trades.addAll(fileTrades);
                fileTrades.clear();
            }
            catch (Exception e) {
                log.error(format("Error reading file %s. %s", file, e.getMessage()));
            }
        }
        return trades;
    }
}
