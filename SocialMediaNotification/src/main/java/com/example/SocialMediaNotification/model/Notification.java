package com.example.SocialMediaNotification.model;

import com.example.SocialMediaNotification.enums.NotificationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public Notification(){

    }

    public Notification(Long id, Long userId, String message, LocalDateTime timestamp, NotificationType notificationType) {
        this.id = id;
        this.userId = userId;
        this.message = message;
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

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", notificationType=" + notificationType +
                '}';
    }
}

