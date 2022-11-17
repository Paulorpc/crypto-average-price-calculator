package br.blog.smarti.processor.entity;

import br.blog.smarti.processor.enums.SideEnum;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportOutputTrade extends Trade {

    @CsvBindByName(column = "dateTime")
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @CsvBindByName(column = "side")
    private SideEnum side;

    @CsvBindByName(column = "price")
    private BigDecimal price;

    @CsvBindByName(column = "executed")
    private BigDecimal executed;

    @CsvBindByName(column = "amount")
    private BigDecimal amount;

    @CsvBindByName(column = "fee")
    private String fee;

    @Override
    public String toString() {
        return "ReportOutputTrade{" +
                "dateTime=" + dateTime +
                ", side=" + side +
                ", price=" + price +
                ", executed=" + executed +
                ", amount=" + amount +
                ", fee='" + fee + '\'' +
                "} " + super.toString();
    }
}
