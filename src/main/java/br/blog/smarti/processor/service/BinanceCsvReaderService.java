package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class BinanceCsvReaderService extends CsvReader implements CsvTradesReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BINANCE.getName().toLowerCase();
    
    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BinanceTrade> readAllTradeFiles() {
        List<BinanceTrade> trades = new ArrayList<>();
        List<BinanceTrade> fileTrades = new ArrayList<>();
        List<File> reportsFiles = fileUtils.listFilesFromInputFolder(ExchangesEnum.BINANCE);

        if (CollectionUtils.isEmpty(reportsFiles)) {
            log.error("There are no csv files in folder: {}. The report name must begin with '{}'. Ex: '{}_trades_jan-dec_2021.csv'", fileUtils.getInputFolder(), EXCHANGE_NAME, EXCHANGE_NAME);
        }

        for (File file : reportsFiles) {
            try {
                fileTrades.addAll(this.readTuples(file, BinanceTrade.class));
                fileTrades.stream().forEach(t -> t.setSource(file.getName()));
                trades.addAll(fileTrades);
                fileTrades.clear();
            } catch (Exception e) {
                log.error("Error reading file {}. {}}", file, e.getMessage());
            }
        }
        return trades;
    }
}
