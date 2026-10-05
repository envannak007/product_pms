package com.example.PMS.notification.dto;

import java.time.LocalDateTime;

public record NotificationResponse(
        Integer id,
        String type,
        String title,
        String message,
        Integer referenceId,
        Boolean isRead,
        LocalDateTime createAt
){
}
