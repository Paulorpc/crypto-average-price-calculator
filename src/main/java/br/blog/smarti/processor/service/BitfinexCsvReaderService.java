package br.blog.smarti.processor.service;

import br.blog.smarti.processor.Utils.FileUtils;
import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import ch.qos.logback.core.util.FileUtil;
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
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@Service
public class BitfinexCsvReaderService extends CsvReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BITFINEX.getName().toLowerCase();
    
    @Autowired
    private FileUtils fileUtils;

    public List<BitfinexTrade> readAllTradeFiles() throws FileNotFoundException {
        return readAllTradeFiles(null);
    }

    public List<BitfinexTrade> readAllTradeFiles(String customPath) throws FileNotFoundException {
        List<BitfinexTrade> trades = new ArrayList<>();
        List<BitfinexTrade> fileTrades = new ArrayList<>();
        
        File inputFolder = fileUtils.getInputFolder(customPath);
        List<File> reportsFiles = fileUtils.listFilesFromFolder(inputFolder, ExchangesEnum.BINANCE);

        if(CollectionUtils.isEmpty(reportsFiles)) {
            String errorMSg = format("There are no csv files in folder: %s. Your trade report name must begin with '%s'. Ex: '%s_trades_jan-dec_2021.csv'", inputFolder, EXCHANGE_NAME, EXCHANGE_NAME);
            log.error(errorMSg);
            throw new FileNotFoundException(errorMSg);
        }

        for (File file : reportsFiles) {
            try {
                fileTrades.addAll(this.readTuples(file, BitfinexTrade.class));
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
