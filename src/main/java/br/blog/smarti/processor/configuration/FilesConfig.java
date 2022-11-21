package br.blog.smarti.processor.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class FilesConfig {
    @Value("${files.supported.extension}")
    private List<String> filesSupportedExtension;

    @Value("${files.input.path}")
    private Path filesInputPath;

    @Value("${files.output.path}")
    private Path filesOutputPath;

    @Value("${files.output.name}")
    private String filesOutputName;

    private Path filesCustomPath;

    public String getFilesSupportedExtension() {
        return filesSupportedExtension.get(0);
    }

    public String getFilesInputPath() {
        return filesInputPath.toString();
    }

    public String getFilesOutputPath() {
        return filesOutputPath.toString();
    }

    public String getFilesOutputName() {
        return filesOutputName.replace("{ID}", "00");
    }

    public String getFilesCustomPath() {
        return filesCustomPath.toString();
    }

    public void setFilesCustomPath(String filesCustomPath) {
        if (!StringUtils.isBlank(filesCustomPath)) {
            this.filesCustomPath = Paths.get(filesCustomPath);
        }
    }

    public boolean hasCustomPath() {
        return !StringUtils.isBlank(getFilesCustomPath());
    }
}
