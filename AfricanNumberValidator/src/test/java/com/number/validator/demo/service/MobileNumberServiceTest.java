package com.number.validator.demo.service;

import com.number.validator.demo.constant.FixStatus;
import com.number.validator.demo.constant.Status;
import com.number.validator.demo.entity.MobileNumber;
import com.number.validator.demo.entity.request.MobileNumberRequest;
import com.number.validator.demo.repository.MobileNumbersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MobileNumberServiceTest {

    @InjectMocks
    MobileNumberService  mobileNumberService;

    @Mock
    SouthAfricaNumberValidateService southAfricaNumberValidateService;

    @Mock
    MobileNumbersRepository mobileNumbersRepository;


    @Test
    public void convertToMobileNumber() {
        StatisticalResultCreator statisticalResultCreator = new StatisticalResultCreator();

        List<MobileNumberRequest> mobileNumberRequests = new ArrayList();
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();
        mobileNumberRequest.setId("103426733");
        mobileNumberRequest.setSmsPhone("27736529279");
        mobileNumberRequests.add(mobileNumberRequest);

        MobileNumberRequest mobileNumberRequest2 = new MobileNumberRequest();
        mobileNumberRequest2.setId("103426922");
        mobileNumberRequest2.setSmsPhone("27826088289");
        mobileNumberRequests.add(mobileNumberRequest2);

        MobileNumber mobileNumber = new MobileNumber();
        mobileNumber.setId(mobileNumberRequest.getId());
        mobileNumber.setSmsPhone(mobileNumberRequest.getSmsPhone());
        mobileNumber.setStatus(Status.VALID);
        mobileNumber.setFixStatus(FixStatus.ALREADY_VALID);

        when(southAfricaNumberValidateService.isMatches(mobileNumber)).thenReturn(true);

        assertEquals(true, mobileNumberService.convertToMobileNumber(mobileNumberRequests, statisticalResultCreator, true));
    }

    @Test
    public void notCnvertToMobileNumber() {
        List<MobileNumberRequest> mobileNumberRequests = new ArrayList();

        assertEquals(false, mobileNumberService.convertToMobileNumber(mobileNumberRequests, new StatisticalResultCreator(), true));
    }
}