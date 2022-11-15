package br.blog.smarti.processor.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitfinexTrade extends Trade {

    @CsvBindByName(column = "DATE")
    private String dateTime;

    @CsvBindByName(column = "PAIR")
    private String pair;

    @CsvBindByName(column = "PRICE")
    private String price;

    @CsvBindByName(column = "AMOUNT")
    private String executed;

    @CsvBindByName(column = "FEE")
    private String fee;

    @CsvBindByName(column = "FEE PERC")
    private String feePerc;

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


