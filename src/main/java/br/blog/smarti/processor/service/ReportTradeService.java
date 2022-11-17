package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.mappers.ReportOutputMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ReportTradeService {

    @Autowired
    BinanceCsvReader binanceCsvReader;

    @Autowired
    BitfinexCsvReader bitfinexCsvReader;
    ReportOutputMapper mapper = new ReportOutputMapper();
    @Value("${files.output.path}")
    private String filePath;
    @Value("${file.output.name}")
    private String fileName;

    public List<ReportOutputTrade> generateReportOutputTradeContent(ExchangesEnum... exchanges) {

        List<ExchangesEnum> exchangesList = Arrays.asList(exchanges);
        List<Trade> trades = new ArrayList<>();

        if (exchangesList.isEmpty()) {
            throw new IllegalArgumentException("you must indicate wich reports you want to process");
        }

        if (exchangesList.contains(ExchangesEnum.BINANCE)) {
            trades.addAll(binanceCsvReader.readAllTradeFiles());
        }
        if (exchangesList.contains(ExchangesEnum.BITFINEX)) {
            trades.addAll(bitfinexCsvReader.readAllTradeFiles());
        }

        return trades.stream()
                .map(mapper::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void generateReportOutputTradeCsv(List<ReportOutputTrade> trades) {
        try (FileWriter writer = new FileWriter(filePath.concat(fileName))) {
            StatefulBeanToCsv<ReportOutputTrade> beanToCsv = new StatefulBeanToCsvBuilder<ReportOutputTrade>(writer)
                    .withSeparator(',')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(trades);
        } catch (Exception e) {
            log.error("Error generating report output. " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
