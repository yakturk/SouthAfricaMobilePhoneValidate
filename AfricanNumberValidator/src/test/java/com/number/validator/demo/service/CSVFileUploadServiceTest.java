package com.number.validator.demo.service;

import com.number.validator.demo.entity.request.MobileNumberRequest;
import com.number.validator.demo.exception.InvalidFileFormatException;
import com.number.validator.demo.exception.NotAcceptableFileExtension;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CSVFileUploadServiceTest {

    private static final String INVALID_FILE_TXT = "invalidFile.txt";
    private static final String INVALID_FILE_CSV = "invalidFile2.csv";
    private static final String VALIDATE_NUMBER = "validateNumber.csv";
    private static final String VALIDATE_NUMBERS = "validateNumbers.csv";

    private MultipartFile invalidFile1;
    private MultipartFile invalidFile2;
    private MultipartFile validFile1;
    private MultipartFile validFile2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    CSVFileUploadService csvFileUploadService;

    @Before
    public void setUp() throws Exception {
        File copyInvalidFile1 = new File(this.getClass().getClassLoader()
                .getResource(INVALID_FILE_TXT).toURI());

        File copyInvalidFile2 = new File(this.getClass().getClassLoader()
                .getResource(INVALID_FILE_CSV).toURI());

        File copyValidFile1 = new File(this.getClass().getClassLoader()
                .getResource(VALIDATE_NUMBER).toURI());

        File copyValidFile2 = new File(this.getClass().getClassLoader()
                .getResource(VALIDATE_NUMBERS).toURI());

        this.invalidFile1 = convertIntoMultiPartFile(copyInvalidFile1);
        this.invalidFile2 = convertIntoMultiPartFile(copyInvalidFile2);
        this.validFile1 = convertIntoMultiPartFile(copyValidFile1);
        this.validFile2 = convertIntoMultiPartFile(copyValidFile2);
    }

    @After
    public void tearDown() {
        this.invalidFile1 = null;
        this.invalidFile2 = null;
        this.validFile1 = null;
        this.validFile2 = null;
    }

    @Test
    public void shouldThrowNotAcceptableFileExtensionException() throws Exception{
        thrown.expect(NotAcceptableFileExtension.class);
        csvFileUploadService.processCsvFile(invalidFile1);
    }

    @Test
    public void shouldThrowInvalidFileFormatException() throws Exception {
        thrown.expect(InvalidFileFormatException.class);
        csvFileUploadService.processCsvFile(invalidFile2);
    }

    @Test
    public void shouldReturnListSizeOne() throws Exception {
        List<MobileNumberRequest> expectedList = new ArrayList();
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();
        mobileNumberRequest.setId("103426733");
        mobileNumberRequest.setSmsPhone("27736529279");
        expectedList.add(mobileNumberRequest);

        List<MobileNumberRequest> result = csvFileUploadService.processCsvFile(validFile1);
        assertEquals(1,result.size());
        assertEquals(expectedList,result);
    }

    @Test
    public void shouldReturnListSizeFour() throws Exception{
        List<MobileNumberRequest> expectedList = new ArrayList();
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();
        mobileNumberRequest.setId("103426733");
        mobileNumberRequest.setSmsPhone("27736529279");
        expectedList.add(mobileNumberRequest);

        MobileNumberRequest mobileNumberRequest2 = new MobileNumberRequest();
        mobileNumberRequest2.setId("103426922");
        mobileNumberRequest2.setSmsPhone("27826088289");
        expectedList.add(mobileNumberRequest2);

        MobileNumberRequest mobileNumberRequest3 = new MobileNumberRequest();
        mobileNumberRequest3.setId("103425902");
        mobileNumberRequest3.setSmsPhone("27813100776");
        expectedList.add(mobileNumberRequest3);

        MobileNumberRequest mobileNumberRequest4 = new MobileNumberRequest();
        mobileNumberRequest4.setId("103389072");
        mobileNumberRequest4.setSmsPhone("843806126");
        expectedList.add(mobileNumberRequest4);

        List<MobileNumberRequest> result = csvFileUploadService.processCsvFile(validFile2);
        assertEquals(4,result.size());
        assertEquals(expectedList,result);
    }

    private MultipartFile convertIntoMultiPartFile(File file){
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(file.toPath());
        } catch (final IOException e) {
        }
        return new MockMultipartFile(file.getName(), file.getName(), contentType, content);
    }

}