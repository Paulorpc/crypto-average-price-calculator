package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.mappers.ReportOutputMapper;
import br.blog.smarti.processor.utils.FileUtils;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ReportTradeService {

    private static final char COMMA = ',';

    @Autowired
    private List<CsvTradesReader> csvTradesReader;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ReportOutputMapper mapper;

    public List<ReportOutputTrade> generateReportOutputTradeContent(ExchangesEnum... exchanges) {
        List<ExchangesEnum> exchangesList = Arrays.asList(exchanges);
        List<Trade> trades = new ArrayList<>();

        if (exchangesList.isEmpty()) {
            csvTradesReader.stream().forEach(csvTradesReader -> trades.addAll(csvTradesReader.readAllTradeFiles()));
        } else {
            exchangesList.forEach(exchangeEnum -> csvTradesReader.stream()
                    .filter(reader -> isCsvReaderFromExchange(reader, exchangeEnum))
                    .findAny()
                    .ifPresent(csvTradesReader -> trades.addAll(csvTradesReader.readAllTradeFiles())));
        }

        Long filesReaded = trades.stream().map(Trade::getSource).distinct().count();
        log.info("Imported [{}] files and [{}] Trades", filesReaded, trades.size());

        return trades.stream()
                .map(mapper::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    private boolean isCsvReaderFromExchange(CsvTradesReader reader, ExchangesEnum exchanges) {
        return reader.getClass().getCanonicalName().contains(exchanges.getName());
    }

    public void generateReportOutputTradeCsv(ExchangesEnum... exchanges) {
        List<ReportOutputTrade> trades = generateReportOutputTradeContent(exchanges);
        File outputFileNamePath = fileUtils.getOutputFilePathName();

        try (FileWriter writer = new FileWriter(outputFileNamePath)) {
            StatefulBeanToCsv<ReportOutputTrade> beanToCsv = new StatefulBeanToCsvBuilder<ReportOutputTrade>(writer)
                    .withSeparator(COMMA)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(trades);
            log.info("Trades Report generated with success!");
            log.info("Destination path: " + outputFileNamePath);
        } catch (Exception e) {
            log.error("Error generating report output. {}", e.getMessage());
            log.debug(e);
            throw new RuntimeException(e);
        }
    }
}
