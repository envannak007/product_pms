
package com.example.PMS.notification.controller;

import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.notification.dto.NotificationResponse;
import com.example.PMS.notification.dto.UnreadCountResponse;
import com.example.PMS.notification.service.NotificationService;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;


    // =========================================================
    // GET ALL NOTIFICATIONS
    // =========================================================

    @GetMapping
    public ResponseEntity<BaseResponse<List<NotificationResponse>>>
    getNotifications(Authentication authentication) {

        UserEntity user = getCurrentUser(authentication);

        List<NotificationResponse> notifications =
                notificationService.getNotifications(user);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Get notifications successfully.",
                        notifications
                )
        );
    }


    // =========================================================
    // GET UNREAD COUNT
    // =========================================================

    @GetMapping("/unread-count")
    public ResponseEntity<BaseResponse<UnreadCountResponse>>
    getUnreadCount(Authentication authentication) {

        UserEntity user = getCurrentUser(authentication);

        long count =
                notificationService.getUnreadCount(user);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Get unread notification count successfully.",
                        new UnreadCountResponse(count)
                )
        );
    }


    // =========================================================
    // MARK ONE AS READ
    // =========================================================

    @PatchMapping("/{id}/read")
    public ResponseEntity<BaseResponse<Void>>
    markAsRead(
            @PathVariable Integer id,
            Authentication authentication
    ) {

        UserEntity user = getCurrentUser(authentication);

        notificationService.markAsRead(id, user);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Notification marked as read.",
                        null
                )
        );
    }


    // =========================================================
    // MARK ALL AS READ
    // =========================================================

    @PatchMapping("/read-all")
    public ResponseEntity<BaseResponse<Void>>
    markAllAsRead(Authentication authentication) {

        UserEntity user = getCurrentUser(authentication);

        notificationService.markAllAsRead(user);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "All notifications marked as read.",
                        null
                )
        );
    }


    // =========================================================
    // DELETE ONE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>
    deleteNotification(
            @PathVariable Integer id,
            Authentication authentication
    ) {

        UserEntity user = getCurrentUser(authentication);

        notificationService.deleteNotification(id, user);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Notification deleted.",
                        null
                )
        );
    }


    // =========================================================
    // DELETE ALL
    // =========================================================

    @DeleteMapping("/clear-all")
    public ResponseEntity<BaseResponse<Void>>
    deleteAllNotifications(Authentication authentication) {

        UserEntity user = getCurrentUser(authentication);

        notificationService.deleteAllNotification(user);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "All notifications deleted.",
                        null
                )
        );
    }


    // =========================================================
    // GET CURRENT USER
    // =========================================================

    private UserEntity getCurrentUser(
            Authentication authentication
    ) {

        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}

