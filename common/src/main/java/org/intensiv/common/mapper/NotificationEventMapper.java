package org.intensiv.common.mapper;

import org.intensiv.common.dto.notification.NotificationEvent;
import org.intensiv.common.dto.notification.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper()
public interface NotificationEventMapper {
    NotificationEventMapper INSTANCE = Mappers.getMapper(NotificationEventMapper.class);

    NotificationEvent toNotificationEvent(Operation operation, String email);

    default Map<String,String> toEmailMessage(Operation operation, Map<String, String> texts) {
        String subject = texts.get("subject");
        String body = switch (operation) {
            case CREATED -> texts.get("account-created");
            case DELETED -> texts.get("account-deleted");
        };
        return Map.of("subject",subject, "body",body);
    }
}
