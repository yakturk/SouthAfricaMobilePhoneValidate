package com.number.validator.demo.entity.request;

import lombok.Data;

import java.util.Objects;

@Data
public class MobileNumberRequest {
    private String id;
    private String smsPhone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobileNumberRequest)) return false;
        MobileNumberRequest that = (MobileNumberRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(smsPhone, that.smsPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, smsPhone);
    }
}
