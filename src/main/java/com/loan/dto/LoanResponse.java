package com.loan.dto;

import lombok.Data;

@Data
public class LoanResponse {

	  private String loanStatus;
	  private Double sanctionedAmount = 0.00;
	  private String comments;
}
