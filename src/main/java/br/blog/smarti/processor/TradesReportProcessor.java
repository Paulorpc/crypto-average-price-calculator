package br.blog.smarti.processor;

import br.blog.smarti.processor.configuration.FilesConfig;
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
public class TradesReportProcessor implements ApplicationRunner {
    
    @Autowired
    private FilesConfig filesConfig;

    @Autowired
    private TradesReportController tradesReportController;

    public static void main(String[] args) {
        SpringApplication.run(TradesReportProcessor.class, args);
    }

    @Override
    public void run(ApplicationArguments arg) throws Exception {
        try {
            filesConfig.setFilesCustomPath(getPathOptionParameter(arg));
            tradesReportController.processTradesReports();
        } catch (Exception e) {
            log.error(e.getClass());
            e.printStackTrace();
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
