package com.example.SocialMediaNotification.model;

import com.example.SocialMediaNotification.enums.NotificationType;
import jakarta.persistence.*;


@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long postId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "notified_time")
    private Long notifiedTime;

    public Notification(){

    }

    public Notification(Long id, Long userId, Long postId, NotificationType notificationType, Long notifiedTime) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.notificationType = notificationType;
        this.notifiedTime = notifiedTime;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getNotifiedTime() {
        return notifiedTime;
    }

    public void setNotifiedTime(Long notifiedTime) {
        this.notifiedTime = notifiedTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", notificationType=" + notificationType +
                ", notifiedTime=" + notifiedTime +
                '}';
    }
}

