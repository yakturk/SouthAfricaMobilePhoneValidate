package com.number.validator.demo.entity.response;

import com.number.validator.demo.entity.MobileNumber;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MobileNumberResponse implements Serializable {
    private String name;
    private int count;
    private List<MobileNumber> numbers;
}
