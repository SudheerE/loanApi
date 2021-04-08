package com.loan.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loan.dto.LoanRequest;
import com.loan.dto.LoanResponse;
import com.loan.service.LoanService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoanController.class)
class LoanControllerTest {
	
	@MockBean
	private LoanService loanService;
	
	@Autowired 
	private MockMvc mockMvc;

	@Test
	void test() throws Exception{
		LoanRequest request = new LoanRequest();
		request.setSsnNumber("999-99-9999");
		request.setAnnualIncome(900000.00);
		request.setLoanAmount(400000.00);
		
		when(loanService.process(Mockito.any())).thenReturn(mockLoanResponse());

		String requestJson = new ObjectMapper().writeValueAsString(request);
		mockMvc
        .perform(
            MockMvcRequestBuilders.post("/applyForLoan")
                .contentType("application/json")
                .content(requestJson))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn()
        .getResponse();
		
		
	}

	public LoanResponse mockLoanResponse() {
		LoanResponse loanResponse = new LoanResponse();
	   
		loanResponse.setLoanStatus("ELIGIBLE");
		loanResponse.setSanctionedAmount(450000.00);
	    loanResponse.setComments("");
	    return loanResponse;
	  }
}
