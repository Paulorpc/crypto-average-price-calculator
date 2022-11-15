package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.mappers.ReportOutputMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReportTradeService {

    @Autowired
    BinanceCsvReader binanceCsvReader;

    @Autowired
    BitfinexCsvReader bitfinexCsvReader;

    ReportOutputMapper mapper = new ReportOutputMapper();
    
    public List<ReportOutputTrade> generateReportOutputTradeContent() {

        List<Trade> trades = new ArrayList<>();
        List<BinanceTrade> binanceTrades = binanceCsvReader.readTrades();
        List<BitfinexTrade> bitfinextTrades = bitfinexCsvReader.readTrades();
        trades.addAll(binanceTrades);
        trades.addAll(bitfinextTrades);
        
        return trades.stream()
                .map(mapper::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    
}
