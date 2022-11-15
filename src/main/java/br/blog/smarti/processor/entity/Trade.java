package br.blog.smarti.processor.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Trade {

    @CsvBindByName(column = "pair")
    private String pair;

    @CsvBindByName(column = "exchange")
    private String exchange;

    @CsvBindByName(column = "source")
    private String source;

    @Override
    public String toString() {
        return "Trade{" +
                "pair='" + pair + '\'' +
                ", exchange='" + exchange + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
