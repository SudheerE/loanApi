package com.loan.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.loan.dto.LoanResponse;
import com.loan.service.CreditEngineService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CreditEngineController.class)
class CreditEngineControllerTest {
	
	@MockBean
	private CreditEngineService creditEngineService;
	
	@Autowired 
	private MockMvc mockMvc;

	@Test
	void test() throws Exception{
		
		mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v0/creditEngine/rating")
                .content("999-99-9999"))
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
