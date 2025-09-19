package org.intensiv.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.intensiv.common.dto.notification.NotificationEvent;
import org.intensiv.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-events")
    public void listen(NotificationEvent event) {
        switch (event.operation()) {
            case CREATED ->
                    emailService.sendEmail(event.email(), "Уведомление от notification service", "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
            case DELETED ->
                    emailService.sendEmail(event.email(), "Уведомление от notification service", "Здравствуйте! Ваш аккаунт был удалён.");
        }
    }
}
