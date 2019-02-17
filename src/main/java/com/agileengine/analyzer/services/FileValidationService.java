package com.agileengine.analyzer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.agileengine.analyzer.utils.HTMLUtils.isValidHTMLFile;

public class FileValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileValidationService.class);

    public boolean validateHtmlFile(File file) {
        boolean valid = file.exists() && isValidHTMLFile(file);
        if (!valid) {
            LOGGER.error("Not valid html file [{}] ", file.getName());
        }
        return valid;
    }

}
