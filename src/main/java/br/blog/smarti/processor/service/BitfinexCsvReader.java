package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.BitfinexTrade;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BitfinexCsvReader extends CsvReader {

    private static final String fileName = "bitfinex_teste.csv";
    private static final String filePath = "c:\\";

    public List<BitfinexTrade> readTrades() {
        
        List<BitfinexTrade> trades = Collections.emptyList();
        try {
            trades  = this.readTuples(filePath + fileName, BitfinexTrade.class);
        }
        catch (Exception e) {
            System.out.println("error");            
        }
        
        return trades;
    }
    
}
