package br.blog.smarti.processor.entity;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceTrade extends Trade {

    @CsvBindByPosition(position = 0)
    private String dateTime;

    @CsvBindByPosition(position = 1)
    private String pair;

    @CsvBindByPosition(position = 2)
    private String side;

    @CsvBindByPosition(position = 3)
    private String price;

    @CsvBindByPosition(position = 4)
    private String executed;

    @CsvBindByPosition(position = 5)
    private String amount;

    @CsvBindByPosition(position = 6)
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
