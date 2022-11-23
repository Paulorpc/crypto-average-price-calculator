package br.blog.smarti.processor.entity;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitfinexTrade extends Trade {
    
    @CsvBindByPosition(position = 1)
    private String pair;

    @CsvBindByPosition(position = 2)
    private String executed;

    @CsvBindByPosition(position = 3)
    private String price;

    @CsvBindByPosition(position = 4)
    private String fee;

    @CsvBindByPosition(position = 5)
    private String feePerc;

    @CsvBindByPosition(position = 6)
    private String feeCurrency;

    @CsvBindByPosition(position = 7)
    private String dateTime;

    @Override
    public String toString() {
        return "BitfinexTrade{" +
                "dateTime='" + dateTime + '\'' +
                ", pair='" + pair + '\'' +
                ", price='" + price + '\'' +
                ", executed='" + executed + '\'' +
                ", fee='" + fee + '\'' +
                ", feePerc='" + feePerc + '\'' +
                "} " + super.toString();
    }
}


