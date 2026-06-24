package com.sniffaround.Interface;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public void send(String to, String subject, String body) throws MessagingException;
}
