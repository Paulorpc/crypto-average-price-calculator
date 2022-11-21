package br.blog.smarti.processor.Utils;

import br.blog.smarti.processor.enums.ExchangesEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

@Component
public class FileUtils {

    @Value("${files.supported.extension}")
    private String fileExtension;

    @Value("${files.input.path}")
    private String inputFilesPath;

    @Value("${files.output.path}")
    private String outputFilesPath;

    @Value("${files.output.name}")
    private String outputFileName;

    public List<File> listFilesFromFolder(File folder, ExchangesEnum exchange) {
        FilenameFilter filter = getFileNameFilter(exchange);
        return Arrays.asList(folder.listFiles(filter));
    }

    private FilenameFilter getFileNameFilter(ExchangesEnum exchangeName) {
        return (dir, name) -> name.toLowerCase().startsWith(exchangeName.getName().toLowerCase()) && name.toLowerCase().endsWith(fileExtension);
    }

    public File getInputFolder(String customPath) {
        return new File(StringUtils.isBlank(customPath) ? inputFilesPath : customPath);
    }
    
    public File getOutputFolder(String customPath) {
        return new File(StringUtils.isBlank(customPath) ? outputFilesPath : guaranteeFinalSlash(customPath));
    }

    public File getOutputFileNamePath(String customPath) {
        String folder = StringUtils.isBlank(customPath) ? outputFilesPath : guaranteeFinalSlash(customPath);
        return new File(folder.concat(outputFileName));
    }
    
    // todo create a generic solution
    private String guaranteeFinalSlash(String path) {
        return path.endsWith("\\") ? path : path.concat("\\");
    }
}
