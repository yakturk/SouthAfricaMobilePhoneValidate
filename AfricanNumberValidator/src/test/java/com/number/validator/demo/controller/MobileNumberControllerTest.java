package com.number.validator.demo.controller;

import com.number.validator.demo.entity.request.MobileNumberRequest;
import com.number.validator.demo.exception.InvalidFileFormatException;
import com.number.validator.demo.exception.NotAcceptableFileExtension;
import com.number.validator.demo.service.CSVFileUploadService;
import com.number.validator.demo.service.MobileNumberService;
import com.number.validator.demo.service.StatisticalResultCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MobileNumberControllerTest {

    private MultipartFile validFile2 = null;

    @InjectMocks
    MobileNumberController mobileNumberController;

    @Mock
    CSVFileUploadService csvFileUploadService;

    @Mock
    MobileNumberService mobileNumberService;


    @Test
    public void shouldReturnOkWhenValidateNumbers() throws Exception{
        List<MobileNumberRequest> mobileNumberRequests = new ArrayList();

        when(csvFileUploadService.processCsvFile(validFile2)).thenReturn(mobileNumberRequests);
        when(mobileNumberService.convertToMobileNumber(mobileNumberRequests, new StatisticalResultCreator(), true)).thenReturn(true);
        ResponseEntity response = mobileNumberController.validateNumbers(this.validFile2);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void shouldReturnUnsupportedMediaType() throws Exception{
        when(csvFileUploadService.processCsvFile(validFile2)).thenThrow(new NotAcceptableFileExtension());
        ResponseEntity response = mobileNumberController.validateNumbers(this.validFile2);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE,response.getStatusCode());
    }

    @Test
    public void shouldReturnNotAcceptable() throws Exception{
        when(csvFileUploadService.processCsvFile(validFile2)).thenThrow(new InvalidFileFormatException());
        ResponseEntity response = mobileNumberController.validateNumbers(this.validFile2);

        assertEquals(HttpStatus.NOT_ACCEPTABLE,response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenValidateNumber() throws Exception{
        List<MobileNumberRequest> mobileNumberRequests = new ArrayList();

        when(csvFileUploadService.processCsvFile(validFile2)).thenReturn(mobileNumberRequests);
        when(mobileNumberService.convertToMobileNumber(mobileNumberRequests, new StatisticalResultCreator(), false)).thenReturn(true);
        ResponseEntity response = mobileNumberController.validateNumber(this.validFile2);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

}