package br.blog.smarti.processor.mappers;

import br.blog.smarti.processor.entity.BinanceTrade;
import br.blog.smarti.processor.entity.BitfinexTrade;
import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.entity.Trade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.enums.SideEnum;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Log4j2
public class ReportOutputMapper {

    private static final Pattern ALPHA_UPPERCASE_PATTERN = Pattern.compile("[A-Z]");

    public ReportOutputTrade toEntity(Trade trade) {
        try {
            if (trade instanceof BinanceTrade) {
                return toEntity((BinanceTrade) trade);
            } else if (trade instanceof BitfinexTrade) {
                return toEntity((BitfinexTrade) trade);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(format("Error converting trade data. %s: %s", e.getMessage(), trade));
        }
        return null;
    }
    
    private ReportOutputTrade toEntity(BinanceTrade s) {
        if (s == null) {
            return null;
        }

        ReportOutputTrade d = new ReportOutputTrade();

        d.setDateTime(toDateTime(s.getDateTime()));
        d.setExchange(ExchangesEnum.BINANCE.getName());
        d.setPair(s.getPair());
        d.setSide(SideEnum.valueOf(s.getSide()));
        d.setPrice(toMoney(s.getPrice()));
        d.setExecuted(toMoney(s.getExecuted()));
        d.setAmount(toMoney(s.getAmount()));
        d.setFee(s.getFee());
        d.setSource(s.getSource());

        return d;
    }

    private ReportOutputTrade toEntity(BitfinexTrade s) {
        if (s == null) {
            return null;
        }

        ReportOutputTrade d = new ReportOutputTrade();

        d.setDateTime(toDateTime(s.getDateTime()));
        d.setExchange(ExchangesEnum.BITFINEX.getName());
        d.setPair(toStandardPair(s.getPair()));
        d.setSide(identifySide(s));
        d.setPrice(toMoney(s.getPrice()));
        d.setExecuted(toMoney(s.getExecuted()).abs());
        d.setAmount(calculateAmount(s).abs());
        d.setFee(toFee(s));
        d.setSource(s.getSource());

        return d;
    }
    
    private BigDecimal toMoney(String value) {
        String filtered = value.replaceAll(ALPHA_UPPERCASE_PATTERN.toString(), EMPTY).replace(",", EMPTY);
        return new BigDecimal(filtered);
    }

    private String toStandardPair(String pair) {
        return pair.replace("/", EMPTY).replace(":", EMPTY).replace("\\", EMPTY);
    }
    
    private LocalDateTime toDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    private SideEnum identifySide(BitfinexTrade s) {
        return toMoney(s.getExecuted()).signum() < 0 ? SideEnum.SELL : SideEnum.BUY;
    }

    private BigDecimal calculateAmount(BitfinexTrade s) {
        return toMoney(s.getPrice()).multiply(toMoney(s.getExecuted()));
    }
    
    private String toFee(BitfinexTrade s) {
        return toMoney(s.getFee()).abs().toString().concat(s.getFeeCurrency());
    }

}
