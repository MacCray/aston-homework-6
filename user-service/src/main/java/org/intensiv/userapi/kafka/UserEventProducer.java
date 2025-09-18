package org.intensiv.userapi.kafka;

import lombok.RequiredArgsConstructor;
import org.intensiv.common.dto.notification.NotificationEvent;
import org.intensiv.common.dto.notification.Operation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void produceUserCreated(String email) {
        NotificationEvent event = new NotificationEvent(Operation.CREATED, email);
        kafkaTemplate.send("user-events", event);
    }

    public void produceUserDeleted(String email) {
        NotificationEvent event = new NotificationEvent(Operation.DELETED, email);
        kafkaTemplate.send("user-events", event);
    }
}
