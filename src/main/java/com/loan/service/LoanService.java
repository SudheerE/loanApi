package com.loan.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.dto.LoanRequest;
import com.loan.dto.LoanResponse;
import com.loan.model.LoanDetail;
import com.loan.repository.LoanApiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {
	
	private static final Logger log = LoggerFactory.getLogger(LoanService.class);
	
	@Autowired
	private LoanApiRepository loanApiRepository;
	
	@Autowired
	private CreditEngineService creditEngineService;
	
	public LoanResponse process(LoanRequest loanRequest) throws Exception{
		log.info("Start LoanService -> process : {} ", loanRequest.toString());
		LoanResponse loanResponse = new LoanResponse();
		LoanDetail loanDetail = new LoanDetail();
		
		try {
			loanDetail.setSsnNumber(loanRequest.getSsnNumber());
			
			if(isLoanRequestAllowed(loanRequest.getSsnNumber())) {
			
				loanDetail.setRequestedAmount(loanRequest.getLoanAmount());
				loanDetail.setSanctionedAmount(loanRequest.getAnnualIncome()/2);
				loanDetail.setAnnualIncome(loanRequest.getAnnualIncome());
				loanDetail.setApplicationDate(LocalDateTime.now());
				
				int creditRating = creditEngineService.getCreditScore(loanRequest.getSsnNumber());
				log.info("LoanService : creditRating : {} ", creditRating);
				
				if(creditRating > 700) {			
					loanDetail.setLoanStatus("ELIGIBLE");
					loanDetail.setSanctionedAmount(loanRequest.getAnnualIncome()/2);
				} else {
					loanDetail.setLoanStatus("NOT ELIGIBLE");
					loanDetail.setSanctionedAmount(0.0);
					loanDetail.setRejectReason("Credit rating is less than 701");
		
				}
			
				loanApiRepository.save(loanDetail);
			} else {
				loanDetail.setLoanStatus("NOT ELIGIBLE");
				loanDetail.setRejectReason("Alredy applied for loan within last 30 days");
			}
			loanResponse = prepareLoanResponse(loanDetail);
		} catch(Exception e) {
			log.error("Exception occured in LoanService -> Process : ", e);
			e.printStackTrace();
			throw e;
		}
		log.info("End LoanService -> process : {} ", loanResponse.toString());
		return loanResponse;
	}
	
	private boolean isLoanRequestAllowed(String ssnNumber) {
		int applicationCount = loanApiRepository.getLoanRequestCountBySsn(ssnNumber);
		log.info("LoanService : applicationCount {} ", applicationCount);
		return (applicationCount > 0) ? false: true;
	}
	
	private LoanResponse prepareLoanResponse(
			 LoanDetail loanDetail) {
		LoanResponse loanResponse = new LoanResponse();
		loanResponse.setLoanStatus(loanDetail.getLoanStatus());
		loanResponse.setSanctionedAmount(loanDetail.getSanctionedAmount());
		loanResponse.setComments((loanDetail.getRejectReason()!=null?loanDetail.getRejectReason():"Success"));
		
		return loanResponse;
	}

}
