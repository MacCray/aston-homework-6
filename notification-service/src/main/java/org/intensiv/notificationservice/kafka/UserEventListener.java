package org.intensiv.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.intensiv.common.dto.notification.NotificationEvent;
import org.intensiv.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {
    private final EmailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic.user-events}")
    public void listen(NotificationEvent event) {
        log.info("Получено сообщение из Kafka: {}", event);
        emailService.sendEmail(event);
    }
}
