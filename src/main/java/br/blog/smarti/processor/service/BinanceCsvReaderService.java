package br.blog.smarti.processor.service;

import br.blog.smarti.processor.Utils.FileUtils;
import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@Service
public class BinanceCsvReaderService extends CsvReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BINANCE.getName().toLowerCase();
    
    @Autowired
    private FileUtils fileUtils;

    public List<BinanceTrade> readAllTradeFiles() throws FileNotFoundException {
        return readAllTradeFiles(null);
    }
    
    public List<BinanceTrade> readAllTradeFiles(String customPath) throws FileNotFoundException {
        List<BinanceTrade> trades = new ArrayList<>();
        List<BinanceTrade> fileTrades = new ArrayList<>();
        
        File inputFolder = fileUtils.getInputFolder(customPath);
        List<File> reportsFiles = fileUtils.listFilesFromFolder(inputFolder, ExchangesEnum.BITFINEX);

        if(CollectionUtils.isEmpty(reportsFiles)) {
            String errorMSg = format("There are no csv files in folder: %s. Your trade report name must begin with '%s'. Ex: '%s_trades_jan-dec_2021.csv'", inputFolder, EXCHANGE_NAME, EXCHANGE_NAME);
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
