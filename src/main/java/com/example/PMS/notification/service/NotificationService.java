
package com.example.PMS.notification.service;

import com.example.PMS.notification.dto.NotificationResponse;
import com.example.PMS.notification.entity.NotificationEntity;
import com.example.PMS.notification.repository.NotificationRepository;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.users.enums.RoleType;
import com.example.PMS.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;


    // =========================================================
    // CREATE NOTIFICATION
    // =========================================================

    public NotificationResponse createNotification(
            UserEntity user,
            String type,
            String title,
            String message,
            Integer referenceId
    ) {

        NotificationEntity notification = NotificationEntity
                .builder()
                .user(user)
                .type(type)
                .title(title)
                .message(message)
                .referenceId(referenceId)
                .isRead(false)
                .build();

        // Save notification
        NotificationEntity saved =
                notificationRepository.save(notification);

        NotificationResponse response =
                new NotificationResponse(
                        saved.getId(),
                        saved.getType(),
                        saved.getTitle(),
                        saved.getMessage(),
                        saved.getReferenceId(),
                        saved.getIsRead(),
                        saved.getCreatedAt()
                );

        // =====================================================
        // REALTIME NOTIFICATION
        // =====================================================

        messagingTemplate.convertAndSend(
                "/topic/notifications",
                response
        );

        // =====================================================
        // UPDATE UNREAD COUNT
        // =====================================================

        sendUnreadCount(user);

        return response;
    }


    // =========================================================
    // NOTIFY ALL ADMINS
    // =========================================================

    public void notifyAdmins(
            String type,
            String title,
            String message,
            Integer referenceId
    ) {

        List<UserEntity> admins =
                userRepository.findByRoles_Name(RoleType.ADMIN);

        for (UserEntity admin : admins) {

            createNotification(
                    admin,
                    type,
                    title,
                    message,
                    referenceId
            );
        }
    }


    // =========================================================
    // GET ALL NOTIFICATIONS
    // =========================================================

    public List<NotificationResponse> getNotifications(
            UserEntity user
    ) {

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notification ->
                        new NotificationResponse(
                                notification.getId(),
                                notification.getType(),
                                notification.getTitle(),
                                notification.getMessage(),
                                notification.getReferenceId(),
                                notification.getIsRead(),
                                notification.getCreatedAt()
                        )
                )
                .toList();
    }


    // =========================================================
    // GET UNREAD COUNT
    // =========================================================

    public long getUnreadCount(
            UserEntity user
    ) {

        return notificationRepository
                .countByUserAndIsReadFalse(user);
    }


    // =========================================================
    // MARK ONE AS READ
    // =========================================================

    @Transactional
    public void markAsRead(
            Integer notificationId,
            UserEntity user
    ) {

        int updated = notificationRepository.markAsRead(
                notificationId,
                user
        );

        log.info(
                "Mark notification as read: id={}, userId={}, updated={}",
                notificationId,
                user.getId(),
                updated
        );

        if (updated == 0) {
            throw new RuntimeException(
                    "Notification not found or does not belong to this user"
            );
        }

        sendUnreadCount(user);
    }


    // =========================================================
    // MARK ALL AS READ
    // =========================================================

    @Transactional
    public void markAllAsRead(
            UserEntity user
    ) {

        notificationRepository.markAllAsRead(user);

        sendUnreadCount(user);
    }


    // =========================================================
    // DELETE ONE
    // =========================================================

    @Transactional
    public void deleteNotification(
            Integer notificationId,
            UserEntity user
    ) {

        notificationRepository.deleteByIdAndUser(
                notificationId,
                user
        );

        sendUnreadCount(user);
    }


    // =========================================================
    // DELETE ALL
    // =========================================================

    @Transactional
    public void deleteAllNotification(
            UserEntity user
    ) {

        notificationRepository.deleteByUser(user);

        sendUnreadCount(user);
    }


    // =========================================================
    // SEND UNREAD COUNT
    // =========================================================

    private void sendUnreadCount(
            UserEntity user
    ) {

        long unreadCount =
                notificationRepository
                        .countByUserAndIsReadFalse(user);

        messagingTemplate.convertAndSend(
                "/topic/notification/unread/" + user.getId(),
                unreadCount
        );
    }
}

