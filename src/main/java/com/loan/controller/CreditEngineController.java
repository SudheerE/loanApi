package com.loan.controller;

import java.util.Random;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loan.constants.ErrorConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v0/creditEngine")
public class CreditEngineController {

	@GetMapping(value = "/rating")
	public int getCreditRating(@Valid @NotNull @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$", message = ErrorConstants.ERROR_VALID_SSN_NUMBER) String ssnNumber) {
		Random random = new Random();
		return random.ints(600, 850).findAny().getAsInt();
	}
}
