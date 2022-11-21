package br.blog.smarti.processor.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilesConfig {

    @Value("${files.supported.extension}")
    private String filesSupportedExtension;

    @Value("${files.input.path}")
    private String filesInputPath;

    @Value("${files.output.path}")
    private String filesOutputPath;

    @Value("${files.output.name}")
    private String filesOutputName;

    private String filesCustomPath;

    public String getFilesSupportedExtension() {
        return filesSupportedExtension;
    }

    public String getFilesInputPath() {
        return filesInputPath;
    }

    public String getFilesOutputPath() {
        return filesOutputPath;
    }

    public String getFilesOutputName() {
        return filesOutputName.replace("{ID}", "00");
    }
    
    public String getFilesCustomPath() {
        return filesCustomPath;
    }

    public void setFilesCustomPath(String filesCustomPath) {
        this.filesCustomPath = filesCustomPath;
    }
}
