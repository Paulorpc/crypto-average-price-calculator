package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BinanceTrade;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BinanceCsvReader extends CsvReader {
    
    private static final String fileName = "binance_teste.csv";
    private static final String filePath = "c:\\";
    
    public List<BinanceTrade> readTrades() {
        
        List<BinanceTrade> trades = Collections.emptyList();
        try {
            trades = this.readTuples(filePath + fileName, BinanceTrade.class);
        }
        catch (Exception e) {
            System.out.println("error");
        }
        
        return trades;
    }
}
