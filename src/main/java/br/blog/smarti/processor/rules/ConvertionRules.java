package br.blog.smarti.processor.rules;

import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public interface ConvertionRules {

    static LocalDateTime toDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    static BigDecimal toMoney(String value) {
        String filtered = value.replaceAll("[A-Z]", EMPTY).replace(",", EMPTY);
        return new BigDecimal(filtered);
    }

    static String toStandardPair(String pair) {
        return pair.replace("/", EMPTY).replace(":", EMPTY).replace("\\", EMPTY);
    }

    static String getExchange(String name) {
        return ExchangesEnum.valueOf(name).getName();
    }

    BigDecimal calculateAmount(String... values);

    SideEnum identifySide(String value);

    String toFee(String... values);

    BigDecimal getExecuted(String executed);

}
