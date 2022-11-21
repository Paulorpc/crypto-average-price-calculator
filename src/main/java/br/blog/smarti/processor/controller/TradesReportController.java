package br.blog.smarti.processor.controller;

import br.blog.smarti.processor.service.ReportTradeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
public class TradesReportController {

    @Autowired
    private ReportTradeService reportTradeService;

    /***
     * Process all reports exchange file from input folder and generate a standard csv output report with all exchanges trades.
     * @throws Exception
     */
    public void processTradesReports() throws Exception {
        reportTradeService.generateReportOutputTradeCsv();
    }

}
