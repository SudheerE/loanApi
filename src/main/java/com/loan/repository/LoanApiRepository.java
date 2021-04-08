package com.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loan.model.LoanDetail;

@Repository
public interface LoanApiRepository extends JpaRepository<LoanDetail, Integer>{

	 @Query("SELECT COUNT(1) FROM LoanDetail WHERE ssnNumber = :ssnNumber AND applicationDate > SYSDATE - 30")
	int getLoanRequestCountBySsn(String ssnNumber);
}
