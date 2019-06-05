package com.number.validator.demo.service;

import com.number.validator.demo.constant.FixStatus;
import com.number.validator.demo.constant.Status;
import com.number.validator.demo.entity.MobileNumber;
import com.number.validator.demo.entity.request.MobileNumberRequest;
import com.number.validator.demo.repository.MobileNumbersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileNumberService {

    @Autowired
    private MobileNumbersRepository mobileNumbersRepository;

    @Autowired
    private SouthAfricaNumberValidateService southAfricaNumberValidateService;

    private StatisticalResultCreator statisticalResultCreator;

    private static final Logger logger = LoggerFactory.getLogger(MobileNumberService.class);

    public boolean convertToMobileNumber(List<MobileNumberRequest> mobileNumbers, StatisticalResultCreator statisticalResultCreator, boolean isSaveToDB){
        this.statisticalResultCreator = statisticalResultCreator;
        if(mobileNumbers != null && !mobileNumbers.isEmpty()){
            for(MobileNumberRequest mobileNumberRequest: mobileNumbers){
                createMobileNumber(mobileNumberRequest, isSaveToDB);
            }
            logger.info("All numbers saved successfully.");
            return true;
        }
        logger.info("Because of empty list, any number could not be saved.");
        return false;
    }

    private void createMobileNumber(MobileNumberRequest mobileNumberRequest, boolean isSaveToDB){
        MobileNumber mobileNumber = new MobileNumber();
        mobileNumber.setId(mobileNumberRequest.getId());
        mobileNumber.setSmsPhone(mobileNumberRequest.getSmsPhone());
        setStatuses(mobileNumber);
        if(isSaveToDB) mobileNumbersRepository.save(mobileNumber);
    }

    private void setStatuses(MobileNumber mobileNumber){
        if(southAfricaNumberValidateService.isMatches(mobileNumber)) {
            mobileNumber.setStatus(Status.VALID);
            mobileNumber.setFixStatus(FixStatus.ALREADY_VALID);
            statisticalResultCreator.addValidNumbers(mobileNumber);
        }
        else if(southAfricaNumberValidateService.isFixed(mobileNumber)){
            mobileNumber.setStatus(Status.FIXED);
            mobileNumber.setFixStatus(FixStatus.PREFIX_ADDED);
            statisticalResultCreator.addFixedNumbers(mobileNumber);
            logger.info("Mobile Number was fixed: " + mobileNumber.getId());
        }
        else{
            mobileNumber.setStatus(Status.INVALID);
            mobileNumber.setFixStatus(FixStatus.NOT_VALIDATED_NOT_APPROPRIATE_FOR_REGEX);
            statisticalResultCreator.addInvalidNumbers(mobileNumber);
        }
    }
}
