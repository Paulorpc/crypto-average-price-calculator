package br.blog.smarti.processor.service;

import br.blog.smarti.processor.Utils.FileUtils;
import br.blog.smarti.processor.entity.BitfinexTrade;
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
public class BitfinexCsvReaderService extends CsvReader implements CsvTradesReader {

    private static final String EXCHANGE_NAME = ExchangesEnum.BITFINEX.getName().toLowerCase();

    @Autowired
    private FileUtils fileUtils;

    public List<BitfinexTrade> readAllTradeFiles() throws FileNotFoundException {
        return readAllTradeFiles(null);
    }

    @Override
    public List<BitfinexTrade> readAllTradeFiles(String customPath) {
        List<BitfinexTrade> trades = new ArrayList<>();
        List<BitfinexTrade> fileTrades = new ArrayList<>();

        File inputFolder = fileUtils.getInputFolder(customPath);
        List<File> reportsFiles = fileUtils.listFilesFromFolder(inputFolder, ExchangesEnum.BITFINEX);

        if (CollectionUtils.isEmpty(reportsFiles)) {
            log.error("There are no csv files in folder: {}. The report name must begin with '{}'. Ex: '{}_trades_jan-dec_2021.csv'", inputFolder, EXCHANGE_NAME, EXCHANGE_NAME);
        }

        for (File file : reportsFiles) {
            try {
                fileTrades.addAll(this.readTuples(file, BitfinexTrade.class));
                fileTrades.stream().forEach(t -> t.setSource(file.getName()));
                trades.addAll(fileTrades);
                fileTrades.clear();
            } catch (Exception e) {
                log.error("Error reading file {}. {}", file, e.getMessage());
            }
        }
        return trades;
    }
}
