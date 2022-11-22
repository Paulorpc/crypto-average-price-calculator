package br.blog.smarti.processor.rules;

import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.util.Objects;

public class BitfinexConvertionRulesImpl implements ConvertionRules {

    /**** 
     * @param values price, executed
     */
    @Override
    public BigDecimal calculateAmount(String... values) {
        Objects.requireNonNull(values[0]);
        Objects.requireNonNull(values[1]);
        return ConvertionRules.toMoney(values[0]).multiply(ConvertionRules.toMoney(values[1])).abs();
    }

    @Override
    public SideEnum identifySide(String value) {
        return ConvertionRules.toMoney(value).signum() < 0 ? SideEnum.SELL : SideEnum.BUY;
    }

    /***
     * @param values fee, feeCurrency 
     */
    @Override
    public String toFee(String... values) {
        Objects.requireNonNull(values[0]);
        Objects.requireNonNull(values[1]);
        return ConvertionRules.toMoney(values[0]).abs().toString().concat(values[1]);
    }

    @Override
    public BigDecimal getExecuted(String executed) {
        return ConvertionRules.toMoney(executed).abs();
    }
}
