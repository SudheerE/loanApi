package com.loan;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loan.dto.LoanRequest;
import com.loan.service.CreditEngineService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoanApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class LoanApplicationTests {

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private CreditEngineService creditEngineService;
	
	LoanRequest request;
	
	@BeforeEach
	public void setUp() {
		request = new LoanRequest();
		request.setSsnNumber("999-99-9999");
		request.setAnnualIncome(900000.00);
		request.setLoanAmount(400000.00);
	}
	  
	@Test
	void contextLoads() {
		assertNotNull("Loading application contexts", this.appContext);
	}

	@Test
	void applyForLoanWithValidParams() throws Exception {
		when(creditEngineService.getCreditScore(anyString())).thenReturn(701);
	    mockMvc
	        .perform(
	            post("/applyForLoan")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(request)))
	        .andExpect(status().is2xxSuccessful());
	}
	
	@Test
	void applyForLoanWithInvalidParams() throws Exception {
		request.setSsnNumber("999-99-999");
		mockMvc
	        .perform(
	            post("/applyForLoan")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(request)))
	        .andExpect(status().isBadRequest());
	}
}
