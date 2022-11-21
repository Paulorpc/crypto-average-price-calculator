package br.blog.smarti.processor.utils;

import br.blog.smarti.processor.configuration.FilesConfig;
import br.blog.smarti.processor.enums.ExchangesEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

@Component
public final class FileUtils {

    @Autowired
    private FilesConfig filesConfig;

    public List<File> listFilesFromInputFolder(ExchangesEnum exchange) {
        FilenameFilter filter = getFileNameFilter(exchange);
        File[] files = getInputFolder().listFiles(filter);
        if (files == null) {
            throw new RuntimeException("There is no files in input folder");
        }
        return Arrays.asList(files);
    }

    private FilenameFilter getFileNameFilter(ExchangesEnum exchangeName) {
        return (dir, name) -> name.toLowerCase().startsWith(exchangeName.getName().toLowerCase()) &&
                name.toLowerCase().endsWith(filesConfig.getFilesSupportedExtension());
    }

    public File getInputFolder() {
        File inputFolder = new File(StringUtils.isBlank(filesConfig.getFilesCustomPath()) ? 
                filesConfig.getFilesInputPath() : filesConfig.getFilesCustomPath());
        validateDirectory(inputFolder);
        return inputFolder;
    }

    public File getOutputFolder() {
        File outputFolder = new File(StringUtils.isBlank(filesConfig.getFilesCustomPath()) ?
                filesConfig.getFilesOutputPath() : filesConfig.getFilesCustomPath());
        validateDirectory(outputFolder);
        return outputFolder;
    }
    
    private void validateDirectory(File folder) {
        if (!folder.isDirectory()) {
            throw new RuntimeException("Input folder not found or it's not a directory: " + folder.getPath());
        }
    }

    public File getOutputFileNamePath() {
        String folder = getOutputFolder().getAbsolutePath();
        return new File(guaranteeFinalSlash(folder).concat(filesConfig.getFilesOutputName()));
    }

    // todo create a generic solution
    private String guaranteeFinalSlash(String path) {
        return path.endsWith("\\") ? path : path.concat("\\");
    }
}
