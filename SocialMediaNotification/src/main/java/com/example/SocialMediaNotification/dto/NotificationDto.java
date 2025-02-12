package com.example.SocialMediaNotification.dto;

import com.example.SocialMediaNotification.enums.NotificationType;
import java.time.LocalDateTime;


public class NotificationDto {


    private Long id;

    private Long userId;

    private String message;

    private boolean read;

    private LocalDateTime timestamp;

    private NotificationType notificationType;

    public NotificationDto(){

    }

    public NotificationDto(Long id, Long userId, String message, boolean read, LocalDateTime timestamp, NotificationType notificationType) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.read = read;
        this.timestamp = timestamp;
        this.notificationType = notificationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

}

