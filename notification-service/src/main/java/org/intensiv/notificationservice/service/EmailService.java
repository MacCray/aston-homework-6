package org.intensiv.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.intensiv.common.dto.notification.NotificationEvent;
import org.intensiv.common.mapper.NotificationEventMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final MailSender mailSender;
    @Value("#{${notification.message}}")
    private Map<String, String> texts;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("krasikov@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendEmail(NotificationEvent event) {
        Map<String, String> messageContent = NotificationEventMapper.INSTANCE.toEmailMessage(event.operation(), texts);
        sendEmail(event.email(), messageContent.get("subject"), messageContent.get("body"));
    }
}
