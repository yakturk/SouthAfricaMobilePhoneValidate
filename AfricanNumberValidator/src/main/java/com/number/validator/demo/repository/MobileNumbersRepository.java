package com.number.validator.demo.repository;

import com.number.validator.demo.entity.MobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileNumbersRepository extends JpaRepository<MobileNumber, String> {
}
