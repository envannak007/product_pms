
package com.example.PMS.notification.repository;

import com.example.PMS.notification.entity.NotificationEntity;
import com.example.PMS.users.entitty.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<NotificationEntity, Integer> {

    List<NotificationEntity> findByUserOrderByCreatedAtDesc(
            UserEntity user
    );

    long countByUserAndIsReadFalse(
            UserEntity user
    );

    // =========================================================
    // MARK ONE AS READ
    // =========================================================

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE NotificationEntity n
        SET n.isRead = true
        WHERE n.id = :notificationId
          AND n.user = :user
        """)
    int markAsRead(
            @Param("notificationId") Integer notificationId,
            @Param("user") UserEntity user
    );

    // =========================================================
    // MARK ALL AS READ
    // =========================================================

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE NotificationEntity n
        SET n.isRead = true
        WHERE n.user = :user
          AND n.isRead = false
        """)
    int markAllAsRead(
            @Param("user") UserEntity user
    );

    // =========================================================
    // DELETE ONE
    // =========================================================

    @Modifying
    @Query("""
            DELETE FROM NotificationEntity n
            WHERE n.id = :notificationId
              AND n.user = :user
            """)
    int deleteByIdAndUser(
            @Param("notificationId") Integer notificationId,
            @Param("user") UserEntity user
    );

    // =========================================================
    // DELETE ALL
    // =========================================================

    @Modifying
    @Query("""
            DELETE FROM NotificationEntity n
            WHERE n.user = :user
            """)
    void deleteByUser(
            @Param("user") UserEntity user
    );
}

