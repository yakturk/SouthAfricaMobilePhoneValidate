package com.number.validator.demo.service;

import com.number.validator.demo.entity.MobileNumber;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SouthAfricaNumberValidateService {
    private static final String REGEX = "^((?:\\+27|27)|0)(=72|82|73|83|74|84)(\\d{7})$";
    // +27832762842 | 27832762842 | 0832762842

    private static final String START_REGEX = "^((?:\\+27|27)|0).*$";
    private static final String MEDIUM_REGEX = "^(72|82|73|83|74|84)(\\d{7})$";
    private static final String DEFAULT_PREFIX = "27";

    public boolean isMatches(MobileNumber mobileNumber){
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(mobileNumber.getSmsPhone());
        return matcher.matches();
    }

    public boolean isFixed(MobileNumber mobileNumber){
        String number = mobileNumber.getSmsPhone();
        Pattern pattern = Pattern.compile(START_REGEX);
        Matcher matcher = pattern.matcher(number);

        Pattern pattern2 = Pattern.compile(MEDIUM_REGEX);
        Matcher matcher2 = pattern2.matcher(number);

        if(!matcher.matches() && matcher2.matches()){
            mobileNumber.setSmsPhoneAfterFix((DEFAULT_PREFIX + number));
            return true;
        }
        return false;
    }
}
