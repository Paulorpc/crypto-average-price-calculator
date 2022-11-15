package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.mappers.ReportOutputMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sun.source.tree.UsesTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportTradeService {

    @Autowired
    BinanceCsvReader binanceCsvReader;

    @Autowired
    BitfinexCsvReader bitfinexCsvReader;

    @Value("${files.output.path}")
    private String fileOutputPath;

    ReportOutputMapper mapper = new ReportOutputMapper();
    
    public List<ReportOutputTrade> generateReportOutputTradeContent(ExchangesEnum... exchanges) {
        
        List<ExchangesEnum> exchangesList = Arrays.asList(exchanges);
        List<Trade> trades = new ArrayList<>();
        
        if(exchangesList.isEmpty()) {
            throw new IllegalArgumentException("you must indicate wich reports you want to process");
        }
        
        if(exchangesList.contains(ExchangesEnum.BINANCE)) {
            trades.addAll(binanceCsvReader.readAllTradeFiles());
        }
        if(exchangesList.contains(ExchangesEnum.BITFINEX)) {
            trades.addAll(bitfinexCsvReader.readAllTradeFiles());
        }
        
        return trades.stream()
                .map(mapper::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    public void generateReportOutputTradeCsv(List<ReportOutputTrade> trades) {
        try (FileWriter writer = new FileWriter(fileOutputPath)) {
            StatefulBeanToCsv<ReportOutputTrade> beanToCsv = new StatefulBeanToCsvBuilder<ReportOutputTrade>(writer)
                    .withSeparator(',')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(trades);
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
