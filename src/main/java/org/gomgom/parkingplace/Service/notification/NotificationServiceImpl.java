package org.gomgom.parkingplace.Service.notification;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.NotificationDto;
import org.gomgom.parkingplace.Entity.Notification;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.NotificationRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public long createNotification(long userId, String description, String notificationLink) {
        User user = userRepository.findById(userId).orElseThrow();
        Notification notification = new Notification(description, notificationLink, user);
        notificationRepository.save(notification);

        return notification.getId();
    }

    @Override
    public NotificationDto.getNotificationsResponseDto getNotifications(long userId) {
        List<NotificationDto.NotificationDefaultDto> notifications  = notificationRepository.getNotifications(userId);
       return new NotificationDto.getNotificationsResponseDto(notifications);
    }

    @Override
    @Transactional
    public void checkNotification(long userId, long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setChecked(true);
    }

    @Override
    @Transactional
    public void checkAllNotifications(long userId) {
        notificationRepository.checkAllByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteNotification(long userId, long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setUsable(false);
    }

    @Override
    @Transactional
    public void deleteAllNotifications(long userId) {
        notificationRepository.deleteAllByUserId(userId);
    }

}
