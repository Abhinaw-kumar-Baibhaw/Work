package com.example.SocialMediaMessage.Dto;

import com.example.SocialMediaMessage.enums.MessageStatus;
import com.example.SocialMediaMessage.model.Message;
import jakarta.persistence.*;
import java.time.LocalDateTime;


public class MessageDto {

    private Long id;

    private Long senderId;

    private Long receiverId;

    private String content;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;


    public MessageDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.senderId = message.getSenderId() ;
        this.receiverId = message.getReceiverId();
        this.timestamp = message.getTimestamp();
    }

    public MessageDto(Long id, Long senderId, Long receiverId, String content, LocalDateTime timestamp, MessageStatus status) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}