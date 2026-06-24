package com.sniffaround.Service;

import com.sniffaround.Interface.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService implements NotificationService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void send(String to, String subject, String body) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//
//        mailSender.send(message);

        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        String html = """
                <html>
                    <body>
                        <h1>Welcome</h1>
                        <p>Your account has been created</p>
                    </body>
                </html>
                """;
        helper.setText(html, true);
        mailSender.send(message);
    }
}
