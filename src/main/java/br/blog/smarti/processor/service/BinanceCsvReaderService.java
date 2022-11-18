package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@Service
public class BinanceCsvReader extends CsvReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BINANCE.getName().toLowerCase();

    @Value("${files.supported.extension}")
    private String fileExtension;

    @Value("${files.input.path}")
    private String filesPath;

    public List<BinanceTrade> readAllTradeFiles() throws FileNotFoundException {
        return readAllTradeFiles(null);
    }
    
    public List<BinanceTrade> readAllTradeFiles(String customPath) throws FileNotFoundException {
        File inputFolder = new File(StringUtils.isBlank(customPath) ? filesPath : customPath);
        FilenameFilter filter = (dir, name) -> name.toLowerCase().startsWith(EXCHANGE_NAME) && name.toLowerCase().endsWith(fileExtension);
        File[] reportsFiles = inputFolder.listFiles(filter);

        List<BinanceTrade> trades = new ArrayList<>();
        List<BinanceTrade> fileTrades = new ArrayList<>();

        if(reportsFiles == null || reportsFiles.length == 0) {
            String errorMSg = format("There are no csv files in folder: %s. Your trade report name must begin with 'binance'. Ex: 'binance_trades_jan-dec_2021.csv'", inputFolder);
            log.error(errorMSg);
            throw new FileNotFoundException(errorMSg);
        }

        for (File file : reportsFiles) {
            try {
                fileTrades.addAll(this.readTuples(file, BinanceTrade.class));
                fileTrades.stream().forEach(t -> t.setSource(file.getName()));
                trades.addAll(fileTrades);
                fileTrades.clear();
            } catch (Exception e) {
                log.error(format("Error reading file %s. %s", file, e.getMessage()));
            }
        }
        return trades;
    }
}
