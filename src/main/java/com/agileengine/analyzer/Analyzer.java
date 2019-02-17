package com.agileengine.analyzer;

import com.agileengine.analyzer.services.AnalyzeService;
import com.agileengine.analyzer.services.FileValidationService;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

import static com.agileengine.analyzer.domain.Constants.DEFAULT_ELEMENT_ID;
import static com.agileengine.analyzer.utils.HTMLUtils.findElementById;

public class Analyzer {

    private static Logger LOGGER = LoggerFactory.getLogger(Analyzer.class);

    private static FileValidationService fileValidationService = new FileValidationService();
    private static AnalyzeService analyzeService = new AnalyzeService();

    public static void main(String[] args) {
        if (args.length < 2) {
            LOGGER.error("Please provide input parameters as mentioned in README.md");
        } else {
            File originFile = new File(args[0]);
            File changedFile = new File(args[1]);
            boolean originalFileValid = fileValidationService.validateHtmlFile(originFile);
            boolean changedFileValid = fileValidationService.validateHtmlFile(changedFile);

            if (originalFileValid && changedFileValid) {
                Optional<Element> elementOpt = findElementById(originFile, args.length == 3 ? args[2] : DEFAULT_ELEMENT_ID);
                if (elementOpt.isPresent()) {
                    Optional<String> pathOpt = analyzeService.getElementPathInFile(elementOpt.get(), changedFile);
                    LOGGER.info(pathOpt.map(path -> "Path to element: " + path).orElse("Matches not found."));
                } else {
                    LOGGER.error("Failed to find element in original document");
                }
            } else {
                LOGGER.error("Ensure both files provided as run arguments exists and are valid html");
            }
        }
    }
}

