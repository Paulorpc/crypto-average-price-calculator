package br.blog.smarti.processor.rules;

import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class BinanceConvertionRulesImpl implements ConvertionsRules {

    @Override
    public BigDecimal toMoney(String value) {
        String filtered = value.replaceAll("[A-Z]", EMPTY).replace(",", EMPTY);
        return new BigDecimal(filtered);
    }

    @Override
    public String toStandardPair(String pair) {
        return pair.replace("/", EMPTY).replace(":", EMPTY).replace("\\", EMPTY);
    }

    @Override
    public LocalDateTime toDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    /***
     * @param values ammount
     */
    @Override
    public BigDecimal calculateAmount(String... values) {
        Objects.requireNonNull(values[0]);
        return toMoney(values[0]);
    }

    @Override
    public SideEnum identifySide(String value) {
        return SideEnum.valueOf(value);
    }

    /***
     * @param values fee 
     */
    @Override
    public String toFee(String... values) {
        Objects.requireNonNull(values[0]);
        return values[0];
    }

    @Override
    public String getExchange(String name) {
        return ExchangesEnum.valueOf(name).getName();
    }

    @Override
    public BigDecimal getExecuted(String executed) {
        return toMoney(executed);
    }
}
