package br.blog.smarti.processor;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.service.ReportTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AveragePriceCalculatorApplication implements ApplicationRunner {
	
	@Autowired
	ReportTradeService reportTradeService;
	
	public static void main(String[] args) {
		SpringApplication.run(AveragePriceCalculatorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		List<ReportOutputTrade> trades = reportTradeService.generateReportOutputTradeContent(ExchangesEnum.BINANCE, ExchangesEnum.BITFINEX);
		reportTradeService.generateReportOutputTradeCsv(trades);
	}

}
