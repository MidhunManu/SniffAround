package com.sniffaround.Consumer;

import com.sniffaround.DTO.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailConsumer {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @RabbitListener(queues = "email.queue")
    public void send(EmailMessage email) {
        try {
            Context context = new Context();
            context.setVariables(
                    Map.of(
                            "name", "user",
                            "email", email.to()
                    )
            );

            String html = templateEngine.process(
                    "emails/" + email.template(),
                    context
            );

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email.to());
            helper.setSubject(email.subject());
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
