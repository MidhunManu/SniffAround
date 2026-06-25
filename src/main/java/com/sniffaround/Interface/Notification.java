package com.sniffaround.Interface;

import jakarta.mail.MessagingException;

public interface Notification {
    void sendEmail(String to) throws MessagingException;
    default void sendPushNotification() {}
    default void sendSMS() {}
}
