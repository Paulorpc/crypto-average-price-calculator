package br.blog.smarti.processor.rules;

import br.blog.smarti.processor.enums.SideEnum;

import java.math.BigDecimal;
import java.util.Objects;

public class BinanceConvertionRulesImpl implements ConvertionRules {

    /***
     * @param values ammount
     */
    @Override
    public BigDecimal calculateAmount(String... values) {
        Objects.requireNonNull(values[0]);
        return ConvertionRules.toMoney(values[0]);
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
    public BigDecimal getExecuted(String executed) {
        return ConvertionRules.toMoney(executed);
    }
}
