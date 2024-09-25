package org.gomgom.parkingplace.Service.notification;

import org.gomgom.parkingplace.Dto.NotificationDto;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    long createNotification(long userId, String description, String notificationLink);

    NotificationDto.getNotificationsResponseDto getNotifications(long userId, Pageable pageable);

    void checkNotification(long userId, long notificationId);

    void checkAllNotifications(long userId);

    void deleteNotification(long userId, long notificationId);

    void deleteAllNotifications(long userId);

}
