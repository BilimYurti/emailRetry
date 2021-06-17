package com.mhp.coding.challenges.retry.configuration;


import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;
import com.mhp.coding.challenges.retry.core.logic.NotificationService;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;
import com.mhp.coding.challenges.retry.outbound.EmailNotificationSenderService;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableRetry
@EnableAsync
public class GlobalBeanConfiguration {
	
	@Bean
    public NotificationHandler notificationHandler(NotificationSender notificationSender) {
        return new NotificationService(notificationSender);
	}
	
	@Bean
    public NotificationSender sendEmail(JavaMailSender mailSender) {
        return new EmailNotificationSenderService(mailSender);
	}
	/**
	@Bean
	  public RetryTemplate retryTemplate() {
	      RetryTemplate retryTemplate = new RetryTemplate();

	      FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
	      fixedBackOffPolicy.setBackOffPeriod(2000l);
	      retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

	      SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
	      retryPolicy.setMaxAttempts(2);
	      retryTemplate.setRetryPolicy(retryPolicy);

	      return retryTemplate;
	  }
	*/
	@Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialRandomBackOffPolicy expRandomBackOffPolicy = new ExponentialRandomBackOffPolicy();
        expRandomBackOffPolicy.setInitialInterval(5000);
        expRandomBackOffPolicy.setMaxInterval(20000);
        expRandomBackOffPolicy.setMultiplier(2);

        retryTemplate.setBackOffPolicy(expRandomBackOffPolicy);

        Map<Class<? extends Throwable>, Boolean> includeExceptions = new HashMap<>();
        includeExceptions.put(CannotAcquireLockException.class, true);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5, includeExceptions);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    
    }
	
	
    
    

