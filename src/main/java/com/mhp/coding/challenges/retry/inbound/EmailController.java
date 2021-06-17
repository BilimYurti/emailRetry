package com.mhp.coding.challenges.retry.inbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CircuitBreaker
@RequestMapping("/v1/emails")
public class EmailController {
	
	@Autowired
    private NotificationHandler notificationHandler;
    public EmailController(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @PostMapping
    public ResponseEntity<EmailNotification> createEmailNotification(@RequestBody EmailNotification emailNotification) {
        EmailNotification emailNotificationResult = notificationHandler.processEmailNotification(emailNotification);
        return ResponseEntity.ok(emailNotificationResult);
    }
    
    
    	
    
}
