package com.agileengine.analyzer.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.agileengine.analyzer.domain.Constants.CHARSET_NAME;
import static com.agileengine.analyzer.domain.Constants.HTML_EXTENSION;

public class HTMLUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTMLUtils.class);

    public static boolean isValidHTMLFile(File file) {
        return file.getName().endsWith(HTML_EXTENSION);
    }

    public static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        return getDocument(htmlFile).map(document -> document.getElementById(targetElementId));
    }

    public static Optional<Elements> findAllElements(File htmlFile) {
        return getDocument(htmlFile).map(Element::getAllElements);
    }

    private static Optional<Document> getDocument(File htmlFile) {
        try {
            return Optional.of(Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath()));
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
}
