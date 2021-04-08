package com.loan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;

@Service
@Setter
public class CreditEngineService {

	private static final Logger log = LoggerFactory.getLogger(CreditEngineService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${credit_engine.url}")
	private String credieEngineUrl;

    public Integer getCreditScore(String ssnNumber) {
    	log.info("In CreditEngineService -> getCreditScore : credieEngineUrl : {} ", credieEngineUrl);
    	return restTemplate.getForObject(credieEngineUrl, Integer.class);
    }
    
    @Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
