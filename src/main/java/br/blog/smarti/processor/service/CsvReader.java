package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.Trade;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public abstract class CsvReader<T extends Trade> {

    List<T> readTuples(File file, Class<T> clazz) throws FileNotFoundException {

        List<T> tuples = new CsvToBeanBuilder(new FileReader(file))
                .withType(clazz)
                .build()
                .parse();

        return tuples;
    }

}
