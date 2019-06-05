package com.number.validator.demo.service;

import com.number.validator.demo.entity.MobileNumber;
import com.number.validator.demo.entity.response.MobileNumberResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticalResultCreator {
    private static final  String NUMBER_OF_VALID_NUMBERS = "NUMBER OF VALID NUMBERS";
    private static final  String NUMBER_OF_INVALID_NUMBERS = "NUMBER OF INVALID NUMBERS";
    private static final  String NUMBER_OF_FIXED_NUMBERS = "NUMBER OF FIXED NUMBERS";

    private List<MobileNumber> validNumbers;
    private List<MobileNumber> invalidNumbers;
    private List<MobileNumber> fixedNumbers;

    public StatisticalResultCreator(){
        invalidNumbers = new ArrayList<>();
        validNumbers = new ArrayList<>();
        fixedNumbers = new ArrayList<>();
    }

    public void addValidNumbers(MobileNumber mobileNumber){
        validNumbers.add(mobileNumber);
    }
    public void addInvalidNumbers(MobileNumber mobileNumber){
        invalidNumbers.add(mobileNumber);
    }

    public void addFixedNumbers(MobileNumber mobileNumber){
        fixedNumbers.add(mobileNumber);
    }

    public List<MobileNumberResponse> createResponse(){
        List<MobileNumberResponse> response = new ArrayList<>();

        MobileNumberResponse validNumbersResponse = new MobileNumberResponse();
        validNumbersResponse.setCount(this.validNumbers.size());
        validNumbersResponse.setName(NUMBER_OF_VALID_NUMBERS);
        validNumbersResponse.setNumbers(validNumbers);
        response.add(validNumbersResponse);

        MobileNumberResponse invalidNumbersResponse = new MobileNumberResponse();
        invalidNumbersResponse.setCount(this.invalidNumbers.size());
        invalidNumbersResponse.setName(NUMBER_OF_INVALID_NUMBERS);
        invalidNumbersResponse.setNumbers(invalidNumbers);
        response.add(invalidNumbersResponse);

        MobileNumberResponse fixedNumbersResponse = new MobileNumberResponse();
        fixedNumbersResponse.setCount(this.fixedNumbers.size());
        fixedNumbersResponse.setName(NUMBER_OF_FIXED_NUMBERS);
        fixedNumbersResponse.setNumbers(fixedNumbers);
        response.add(fixedNumbersResponse);

        return response;
    }

    public List<MobileNumberResponse> createResponseForOne(){
        List<MobileNumberResponse> response = new ArrayList<>();
        MobileNumberResponse mobileNumberResponse = new MobileNumberResponse();
        if(!validNumbers.isEmpty()){
            mobileNumberResponse.setCount(this.validNumbers.size());
            mobileNumberResponse.setName(NUMBER_OF_VALID_NUMBERS);
            mobileNumberResponse.setNumbers(this.validNumbers);
        }
        else if(!fixedNumbers.isEmpty()){
            mobileNumberResponse.setCount(this.fixedNumbers.size());
            mobileNumberResponse.setName(NUMBER_OF_FIXED_NUMBERS);
            mobileNumberResponse.setNumbers(this.fixedNumbers);
        }
        else{
            mobileNumberResponse.setCount(this.invalidNumbers.size());
            mobileNumberResponse.setName(NUMBER_OF_INVALID_NUMBERS);
            mobileNumberResponse.setNumbers(this.invalidNumbers);
        }
        response.add(mobileNumberResponse);
        return response;
    }
}
