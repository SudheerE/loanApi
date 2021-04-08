package com.loan.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loan.dto.LoanRequest;
import com.loan.dto.LoanResponse;
import com.loan.service.LoanService;

@RestController
public class LoanController {
	
	private static final Logger log = LoggerFactory.getLogger(LoanController.class);
	
	@Autowired
	private LoanService loanService;
	
	@PostMapping("/applyForLoan")
    public LoanResponse process(@RequestBody @Valid LoanRequest loanRequest) throws Exception {
		log.info("In LoanController : {} ", loanRequest.toString());
    	return loanService.process(loanRequest);
    }
    
	
	

}
