package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.Trade;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Log4j2
public abstract class CsvReader<T extends Trade> {
    
    private static final int HEADER = 1;

    List<T> readTuples(File file, Class<T> clazz) throws FileNotFoundException {

        List<T> tuples = new CsvToBeanBuilder(new FileReader(file))
                .withType(clazz)
                .withSkipLines(HEADER)
                .build()
                .parse();

        log.debug(tuples.toString());
        
        return tuples;
    }

}
