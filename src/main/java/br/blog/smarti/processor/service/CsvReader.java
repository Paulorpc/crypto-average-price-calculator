package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.Trade;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public abstract class CsvReader <T extends Trade> {
    
    List<T> readTuples(String fileName, Class<T> clazz) throws FileNotFoundException {

        List<T> tuples = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(clazz)
                .build()
                .parse();
        
        return tuples;
    }
    
}
