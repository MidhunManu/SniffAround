package com.sniffaround.Notification;

import com.sniffaround.DTO.EmailMessage;
import com.sniffaround.Interface.Notification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserRegisteredNotification implements Notification {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendEmail(String to) {
        EmailMessage emailMessage = new EmailMessage(
                to,
                "Welcome to SniffAround",
                "Welcome"
        );

        this.rabbitTemplate.convertAndSend("email.queue", emailMessage);
    }
}
