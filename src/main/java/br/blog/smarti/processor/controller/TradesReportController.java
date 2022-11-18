package br.blog.smarti.processor.controller;

import br.blog.smarti.processor.entity.ReportOutputTrade;
import br.blog.smarti.processor.enums.ExchangesEnum;
import br.blog.smarti.processor.service.ReportTradeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.util.List;

@Log4j2
@Controller
public class TradesReportController {

    @Autowired
    private ReportTradeService reportTradeService;

    /***
     * Process all reports exchange file from input folder and generate a standard csv output report with all exchanges trades. 
     * @param filesPath
     * @throws FileNotFoundException
     */
    public void processTradesReports(String filesPath) throws Exception {
        List<ReportOutputTrade> trades = reportTradeService.generateReportOutputTradeContent(filesPath, ExchangesEnum.BINANCE, ExchangesEnum.BITFINEX);
        reportTradeService.generateReportOutputTradeCsv(filesPath, trades);
    }

}
