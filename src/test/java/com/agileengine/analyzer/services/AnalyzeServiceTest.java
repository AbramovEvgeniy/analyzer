package com.agileengine.analyzer.services;

import com.agileengine.analyzer.utils.HTMLUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.agileengine.analyzer.domain.Constants.DEFAULT_ELEMENT_ID;
import static com.agileengine.analyzer.services.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AnalyzeServiceTest {

    private final File originFile = new File(ClassLoader.getSystemClassLoader().getResource(originalDoc).getFile());
    private final String[] expected = new String[]{
            "html > body > div > div > div > div > div > div > a",
            "html > body > div > div > div > div > div > div > div > a",
            "html > body > div > div > div > div > div > div > a",
            "html > body > div > div > div > div > div > div > a"
    };

    private AnalyzeService analyzeService = new AnalyzeService();

    @Test
    public void testAnalyze() {
        Element element = HTMLUtils.findElementById(originFile, DEFAULT_ELEMENT_ID).get();
        assertEquals(expected[0], analyzeService.getElementPathInFile(element, prepareFile(example1)).get());
        assertEquals(expected[1], analyzeService.getElementPathInFile(element, prepareFile(example2)).get());
        assertEquals(expected[2], analyzeService.getElementPathInFile(element, prepareFile(example3)).get());
        assertEquals(expected[3], analyzeService.getElementPathInFile(element, prepareFile(example4)).get());
    }

    @Test
    public void testNotExistingId() {
        assertFalse(HTMLUtils.findElementById(originFile, "NotExistId").isPresent());
    }

    private File prepareFile(String filePath) {
        return new File(ClassLoader.getSystemClassLoader().getResource(filePath).getFile());
    }

}