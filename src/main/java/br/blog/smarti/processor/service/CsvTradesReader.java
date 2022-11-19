package br.blog.smarti.processor.service;

import br.blog.smarti.processor.entity.Trade;

import java.io.FileNotFoundException;
import java.util.List;

public interface CsvTradesReader<T extends Trade> {
    List<T> readAllTradeFiles(String customPath);
}
