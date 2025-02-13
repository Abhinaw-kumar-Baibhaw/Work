package com.example.SocialMediaNotification.dto;

import com.example.SocialMediaNotification.enums.NotificationType;



public class NotificationDto {

    private Long id;

    private Long userId;

    private Long postId;


    private NotificationType notificationType;

    public NotificationDto(){

    }

    public NotificationDto(Long id, Long userId, Long postId, NotificationType notificationType) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
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

}

