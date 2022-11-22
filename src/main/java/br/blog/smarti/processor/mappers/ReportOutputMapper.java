package br.blog.smarti.processor.mappers;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.rules.BinanceConvertionRulesImpl;
import br.blog.smarti.processor.rules.BitfinexConvertionRulesImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import static java.lang.String.format;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ReportOutputMapper {

    org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(ReportOutputMapper.class);

    BinanceConvertionRulesImpl binanceRules = new BinanceConvertionRulesImpl();
    BitfinexConvertionRulesImpl bitfinexRules = new BitfinexConvertionRulesImpl();

    @Mapping(target = "pair", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.toStandardPair(s.getPair()))")
    @Mapping(target = "price", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.toMoney(s.getPrice()))")
    @Mapping(target = "exchange", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.getExchange(\"BINANCE\"))")
    @Mapping(target = "dateTime", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.toDateTime(s.getDateTime()))")
    @Mapping(target = "side", expression = "java(binanceRules.identifySide(s.getSide()))")
    @Mapping(target = "executed", expression = "java(binanceRules.getExecuted(s.getExecuted()))")
    @Mapping(target = "amount", expression = "java(binanceRules.calculateAmount(s.getAmount(), null))")
    @Mapping(target = "fee", expression = "java(binanceRules.toFee(s.getFee(), null))")
    ReportOutputTrade toEntity(BinanceTrade s);

    @Mapping(target = "pair", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.toStandardPair(s.getPair()))")
    @Mapping(target = "price", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.toMoney(s.getPrice()))")
    @Mapping(target = "exchange", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.getExchange(\"BITFINEX\"))")
    @Mapping(target = "dateTime", expression = "java(br.blog.smarti.processor.rules.ConvertionRules.toDateTime(s.getDateTime()))")
    @Mapping(target = "side", expression = "java(bitfinexRules.identifySide(s.getExecuted()))")
    @Mapping(target = "executed", expression = "java(bitfinexRules.getExecuted(s.getExecuted()))")
    @Mapping(target = "amount", expression = "java(bitfinexRules.calculateAmount(s.getPrice(), s.getExecuted()))")
    @Mapping(target = "fee", expression = "java(bitfinexRules.toFee(s.getFee(), s.getFeeCurrency()))")
    ReportOutputTrade toEntity(BitfinexTrade s);

    default ReportOutputTrade toEntity(Trade trade) {
        try {
            if (trade instanceof BinanceTrade) {
                return toEntity((BinanceTrade) trade);
            } else if (trade instanceof BitfinexTrade) {
                return toEntity((BitfinexTrade) trade);
            }
        } catch (Exception e) {
            log.error(format("Error converting trade data. %s: %s", e.getMessage(), trade));
        }
        return null;
    }
}
