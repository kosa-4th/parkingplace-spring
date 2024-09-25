package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class NotificationDto {

    @RequiredArgsConstructor
    @Getter
    public static class NotificationDefaultDto {
        private final long notificationId;
        private final long userId;
        private final String description;
        private final String notificationLink;
        private final boolean checked;
        private final LocalDateTime createdAt;
    }

    @RequiredArgsConstructor
    @Getter
    public static class getNotificationsResponseDto {
        private final Page<NotificationDefaultDto> notifications;
    }
}
