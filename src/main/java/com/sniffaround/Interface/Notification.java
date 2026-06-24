package com.sniffaround.Interface;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface Notification {
    public void sendEmail(String to) throws MessagingException;
    default public void sendPushNotification() {}
    default public void sendSMS() {}
}
