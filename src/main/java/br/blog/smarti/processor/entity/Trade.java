package br.blog.smarti.processor.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Trade {
    
    private String pair;
    private String exchange;
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
