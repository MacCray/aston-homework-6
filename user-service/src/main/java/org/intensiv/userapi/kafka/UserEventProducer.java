package org.intensiv.userapi.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.intensiv.common.dto.notification.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void produceUserEvent(NotificationEvent event) {
        log.info("Отправка NotificationEvent в Kafka: topic{}, event={}", kafkaTemplate.getDefaultTopic(), event);
        kafkaTemplate.sendDefault(event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Сообщение отправлено успешно: {}", event);
                    } else {
                        log.error("Ошибка при отправке сообщения: {}", event, ex);
                    }
                });
    }
}
