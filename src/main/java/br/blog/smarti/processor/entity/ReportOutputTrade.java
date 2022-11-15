package br.blog.smarti.processor.entity;

import br.blog.smarti.processor.enums.SideEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportOutputTrade extends Trade {
    
    private LocalDateTime dateTime;
    private SideEnum side;
    private BigDecimal price;
    private BigDecimal executed;
    private BigDecimal amount;
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
