package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.NotificationDto;
import org.gomgom.parkingplace.Service.notification.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/notifications/")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/protedted")
    public ResponseEntity<NotificationDto.getNotificationsResponseDto> getNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam long page,
            @PageableDefault(page=0, size=10) Pageable pageable
    ) {
        return ResponseEntity.ok(notificationService.getNotifications(userDetails.getUser().getId(), pageable));
    }

    @PutMapping("/{notificationId}/protected")
    public ResponseEntity<?> checkNotification(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable long notificationId
    ) {
        notificationService.checkNotification(userDetails.getUser().getId(), notificationId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/all/protected")
    public ResponseEntity<?> checkAllNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.checkAllNotifications(userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}/protected")
    public ResponseEntity<?> deleteNotification(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable long notificationId
    ) {
        notificationService.deleteNotification(userDetails.getUser().getId(), notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all/protected")
    public ResponseEntity<?> deleteAllNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        notificationService.deleteAllNotifications(userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

}
