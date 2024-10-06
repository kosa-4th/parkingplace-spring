package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

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
    @Setter
    public static class getNotificationsResponseDto {
        private final List<NotificationDefaultDto> notifications;
        private long uncheckedNotificationCount;
    }

    @RequiredArgsConstructor
    @Getter
    public static class UncheckedNotificationCountResponseDto {
        private final long uncheckedNotificationCount;
    }
}
