package com.mhp.coding.challenges.retry.outbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
@Component
@Primary
@Async
public class EmailNotificationSenderService implements NotificationSender {
	
	private static final Logger log = LoggerFactory.getLogger(EmailNotificationSenderService.class);
	
	
	 int counter = 0;
	

    private static final String SENDER_ADDRESS = "info@mhp.com";

    private JavaMailSender mailSender;

    public EmailNotificationSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Async
    @Override
    @Retryable(value = { RuntimeException.class },maxAttempts = 5,
  backoff=@Backoff(random = true, delay = 5000, maxDelay = 25000, multiplier = 2))  //exponential  backoff    
    public void sendEmail(@Valid @NotNull EmailNotification emailNotification) {
        try {
        	
        	
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(SENDER_ADDRESS);
            mailMessage.setTo(emailNotification.getRecipient());
            mailMessage.setSubject(emailNotification.getSubject());
            mailMessage.setText(emailNotification.getText());
            mailSender.send(mailMessage);
            
            log.info("Executed counter= {}.", counter);
            counter++;
            
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to send email to recipient: %s", emailNotification.getRecipient()));
        }
    }
    
    /**
     * When the retry 5 times all failed, then to recover method, print out the error message
     *
     * @param throwable
     * @param param
      */
    @Recover
    public void recover(Throwable throwable, EmailNotification param) {
        String errorMsg = "Email sending fails to retry 5 times , param = %s, error message : %s";
        throw new IllegalArgumentException(String.format(errorMsg, param, throwable.getMessage()));
    }
    
    
    
}
