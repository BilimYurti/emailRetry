package com.mhp.coding.challenges.retry;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import org.slf4j.Logger;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableRetry
@CircuitBreaker
public class RetryApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(RetryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RetryApplication.class, args);
		LOGGER.info("SpringRetryApplication  started successfully.");
	}
	
	

}
