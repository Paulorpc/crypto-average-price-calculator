package br.blog.smarti.processor.rules;

import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ConvertionsRules {

    BigDecimal toMoney(String value);

    String toStandardPair(String pair);

    LocalDateTime toDateTime(String dateTime);

    BigDecimal calculateAmount(String... values);

    SideEnum identifySide(String value);

    String toFee(String... values);

    String getExchange(String name);

    BigDecimal getExecuted(String executed);

}
