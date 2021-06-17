package com.mhp.coding.challenges.retry.core.inbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;

public interface NotificationHandler {

    EmailNotification processEmailNotification(EmailNotification emailNotification);
}
