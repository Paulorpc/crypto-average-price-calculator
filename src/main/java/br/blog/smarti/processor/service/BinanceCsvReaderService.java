package br.blog.smarti.processor.service;

import br.blog.smarti.processor.Utils.FileUtils;
import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class BinanceCsvReaderService extends CsvReader implements CsvTradesReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BINANCE.getName().toLowerCase();

    @Autowired
    private FileUtils fileUtils;

    public List<BinanceTrade> readAllTradeFiles() throws FileNotFoundException {
        return readAllTradeFiles(null);
    }

    @Override
    public List<BinanceTrade> readAllTradeFiles(String customPath) {
        List<BinanceTrade> trades = new ArrayList<>();
        List<BinanceTrade> fileTrades = new ArrayList<>();

        File inputFolder = fileUtils.getInputFolder(customPath);
        List<File> reportsFiles = fileUtils.listFilesFromFolder(inputFolder, ExchangesEnum.BINANCE);

        if (CollectionUtils.isEmpty(reportsFiles)) {
            log.error("There are no csv files in folder: {}. The report name must begin with '{}'. Ex: '{}_trades_jan-dec_2021.csv'", inputFolder, EXCHANGE_NAME, EXCHANGE_NAME);
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
