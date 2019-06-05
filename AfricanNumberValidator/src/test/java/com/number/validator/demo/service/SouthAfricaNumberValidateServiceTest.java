package com.number.validator.demo.service;

import com.number.validator.demo.entity.MobileNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SouthAfricaNumberValidateServiceTest {

    @InjectMocks
    SouthAfricaNumberValidateService southAfricaNumberValidateService;

    @Test
    public void shouldReturnTrueForValidNumber() {
        MobileNumber mobileNumber = new MobileNumber();
        mobileNumber.setSmsPhone("27826088289");

        assertEquals(true, southAfricaNumberValidateService.isMatches(mobileNumber));
    }

    @Test
    public void shouldReturnfalseForInvalidNumber() {
        MobileNumber mobileNumber = new MobileNumber();
        mobileNumber.setSmsPhone("4984595930440");

        assertEquals(false, southAfricaNumberValidateService.isMatches(mobileNumber));
    }

    @Test
    public void shouldReturnTrueAfterFixInvalidNumber() {
        MobileNumber mobileNumber = new MobileNumber();
        mobileNumber.setSmsPhone("843806126");

        assertEquals(true, southAfricaNumberValidateService.isFixed(mobileNumber));
        assertEquals("27843806126", mobileNumber.getSmsPhoneAfterFix());
    }
}