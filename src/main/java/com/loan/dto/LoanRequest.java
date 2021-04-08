package com.loan.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.loan.constants.ErrorConstants;

import lombok.Data;

@Data
public class LoanRequest {
	
	@NotBlank(message = ErrorConstants.ERROR_SSN_NUMBER)
	@Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$", message = ErrorConstants.ERROR_VALID_SSN_NUMBER)
	private String ssnNumber;

	@NotNull(message = ErrorConstants.ERROR_CURRENT_ANNUAL_INCOME)
	@DecimalMin(value = "0.0", inclusive = false)
	private Double annualIncome;
	
	@NotNull(message = ErrorConstants.ERROR_LOAN_AMOUNT)
	@DecimalMin(value = "0.0", inclusive = false)
	private Double loanAmount;

}
