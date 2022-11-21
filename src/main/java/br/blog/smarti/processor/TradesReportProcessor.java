package br.blog.smarti.processor;

import br.blog.smarti.processor.controller.TradesReportController;
import br.blog.smarti.processor.enums.ArgumentsOptionsEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class AveragePriceCalculatorApplication implements ApplicationRunner {

    @Autowired
    private TradesReportController tradesReportController;

    public static void main(String[] args) {
        SpringApplication.run(AveragePriceCalculatorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments arg) throws Exception {
        try {
            String filesPath = getPathOptionParameter(arg);
            tradesReportController.processTradesReports(filesPath);
        } catch (Exception e) {
            log.error(e.getClass());
            return;
        }
    }

    private String getPathOptionParameter(ApplicationArguments arg) {
        String parameter = null;
        boolean containsParameter = arg.getNonOptionArgs().size() > 0;
        if (arg.containsOption(ArgumentsOptionsEnum.PATH.getValue())) {
            if (!containsParameter) {
                String errorMsg = "Application path's parameter has not been found";
                log.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            parameter = arg.getNonOptionArgs().get(0);
        }
        return parameter;
    }

}
