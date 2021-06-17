package com.mhp.coding.challenges.retry.core.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailNotification {

    @NotBlank
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "EmailNotification [recipient=" + recipient + ", subject=" + subject + ", text=" + text + "]";
	}
    
	
    

}
