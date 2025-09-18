package org.intensiv.notificationservice.kafka;

import org.intensiv.common.dto.notification.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    @KafkaListener(topics = "user-events")
    public void listen(NotificationEvent event) {
        System.out.println("Получено событие: " + event);
    }
}
