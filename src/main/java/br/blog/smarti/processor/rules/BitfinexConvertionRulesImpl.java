package br.blog.smarti.processor.rules;

import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class BitfinexConvertionRulesImpl implements ConvertionsRules {

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

    /**** 
     * @param values price, executed
     */
    @Override
    public BigDecimal calculateAmount(String... values) {
        Objects.requireNonNull(values[0]);
        Objects.requireNonNull(values[1]);
        return toMoney(values[0]).multiply(toMoney(values[1])).abs();
    }

    @Override
    public SideEnum identifySide(String value) {
        return toMoney(value).signum() < 0 ? SideEnum.SELL : SideEnum.BUY;
    }

    /***
     * @param values fee, feeCurrency 
     */
    @Override
    public String toFee(String... values) {
        Objects.requireNonNull(values[0]);
        Objects.requireNonNull(values[1]);
        return toMoney(values[0]).abs().toString().concat(values[1]);
    }

    @Override
    public String getExchange(String name) {
        return ExchangesEnum.valueOf(name).getName();
    }

    @Override
    public BigDecimal getExecuted(String executed) {
        return toMoney(executed).abs();
    }
}
