package com.number.validator.demo.entity;

import com.number.validator.demo.constant.FixStatus;
import com.number.validator.demo.constant.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
public class MobileNumber implements Serializable {
    @Id
    private String id;
    private String smsPhone;
    private Status status = Status.VALID;
    private String smsPhoneAfterFix;
    private FixStatus fixStatus = FixStatus.ALREADY_VALID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobileNumber)) return false;
        MobileNumber that = (MobileNumber) o;
        return id.equals(that.id) &&
                Objects.equals(smsPhone, that.smsPhone) &&
                status == that.status &&
                Objects.equals(smsPhoneAfterFix, that.smsPhoneAfterFix) &&
                fixStatus == that.fixStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, smsPhone, status, smsPhoneAfterFix, fixStatus);
    }
}
