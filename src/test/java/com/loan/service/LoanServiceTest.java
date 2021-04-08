package com.loan.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.loan.dto.LoanRequest;
import com.loan.dto.LoanResponse;
import com.loan.repository.LoanApiRepository;

@SpringBootTest
class LoanServiceTest {

	private static final Logger log = LoggerFactory.getLogger(LoanServiceTest.class);
	
	@Autowired
	private LoanService loanService;
	@MockBean
	private LoanApiRepository loanApiRepository;
	@MockBean
	private RestTemplate restTemplate;
	@MockBean
	private CreditEngineService creditEngineService;
	
	@Test
	void testLoanEligible() throws Exception {
		
		when(loanApiRepository.getLoanRequestCountBySsn(anyString())).thenReturn(0);
		when(creditEngineService.getCreditScore(anyString())).thenReturn(701);
		
		LoanRequest request = new LoanRequest();
		request.setSsnNumber("999-99-9999");
		request.setAnnualIncome(900000.00);
		request.setLoanAmount(400000.00);
		
		LoanResponse loanResponse = loanService.process(request);
		log.info("testLoanEligible : " + loanResponse.toString());
         
	}
	
	@Test
	void testLoanNotEligibleForExistingApplication() throws Exception {
		
		when(loanApiRepository.getLoanRequestCountBySsn(anyString())).thenReturn(1);
		
		LoanRequest request = new LoanRequest();
		request.setSsnNumber("888-88-8888");
		request.setAnnualIncome(900000.00);
		request.setLoanAmount(400000.00);
		
		LoanResponse loanResponse = loanService.process(request);
		log.info("testLoanNotEligibleForExistingApplication : " + loanResponse.toString());
         
	}
	
	@Test
	void testLoanNotEligibleForLowCreditRating() throws Exception {
		
		when(loanApiRepository.getLoanRequestCountBySsn(anyString())).thenReturn(0);
		when(creditEngineService.getCreditScore(anyString())).thenReturn(700);
		
		LoanRequest request = new LoanRequest();
		request.setSsnNumber("777-77-7777");
		request.setAnnualIncome(900000.00);
		request.setLoanAmount(400000.00);
		
		LoanResponse loanResponse = loanService.process(request);
		log.info("testLoanNotEligibleForExistingApplication : " + loanResponse.toString());
         
	}

}
