package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Dto.NotificationDto;
import org.gomgom.parkingplace.Entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT new org.gomgom.parkingplace.Dto.NotificationDto$NotificationDefaultDto(" +
            "noti.id, noti.user.id, noti.description, noti.notificationLink, noti.checked, noti.createdAt" +
            ") " +
            "FROM Notification noti " +
            "WHERE noti.user.id = :userId " +
            "ORDER BY noti.createdAt DESC")
    Page<NotificationDto.NotificationDefaultDto> getNotifications(long userId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notification noti SET noti.checked = true " +
            "WHERE noti.user.id = :userId " +
            "AND noti.checked = false ")
    void checkAllByUserId(long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notification noti SET noti.usable = false " +
            "WHERE noti.user.id = :userId " +
            "AND noti.usable = true")
    void deleteAllByUserId(long userId);
}
