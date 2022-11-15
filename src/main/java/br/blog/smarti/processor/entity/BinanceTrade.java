package br.blog.smarti.processor.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceTrade extends Trade {

    @CsvBindByName(column = "Date(UTC)")
    private String dateTime;

    @CsvBindByName(column = "Pair")
    private String pair;

    @CsvBindByName(column = "Side")
    private String side;

    @CsvBindByName(column = "Price")
    private String price;

    @CsvBindByName(column = "Executed")
    private String executed;

    @CsvBindByName(column = "Amount")
    private String amount;

    @CsvBindByName(column = "Fee")
    private String fee;

    @Override
    public String toString() {
        return "BinanceTrade{" +
                "dateTime='" + dateTime + '\'' +
                ", pair='" + pair + '\'' +
                ", side='" + side + '\'' +
                ", price='" + price + '\'' +
                ", executed='" + executed + '\'' +
                ", amount='" + amount + '\'' +
                ", fee='" + fee + '\'' +
                "} " + super.toString();
    }
}
