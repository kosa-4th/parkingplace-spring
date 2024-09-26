package org.gomgom.parkingplace.notification;

import org.gomgom.parkingplace.Service.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationTest {
    @Autowired
    private NotificationService notificationService;

    @Test
    void add30Noti() {
        for (int i = 0; i < 30; i++) {
            notificationService.createNotification(1L, "더미데이터" + (i + 1), "/");
        }
    }

}
