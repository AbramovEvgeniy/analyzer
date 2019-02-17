package com.agileengine.analyzer.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.agileengine.analyzer.services.TestConstants.notValidFile;
import static com.agileengine.analyzer.services.TestConstants.originalDoc;

class FileValidationServiceTest {

    private FileValidationService fileValidationService = new FileValidationService();

    @Test
    public void testFilesValidation() {
        File originFile = new File(ClassLoader.getSystemClassLoader().getResource(originalDoc).getFile());
        Assertions.assertTrue(fileValidationService.validateHtmlFile(originFile));
    }

    @Test
    public void testFileValidationFileDoesNOtExist() {
        Assertions.assertFalse(fileValidationService.validateHtmlFile(new File("./notExistFile.html")));
    }

    @Test
    public void testFileValidationWrongExtension() {
        File file = new File(ClassLoader.getSystemClassLoader().getResource(notValidFile).getFile());
        Assertions.assertFalse(fileValidationService.validateHtmlFile(file));
    }


}