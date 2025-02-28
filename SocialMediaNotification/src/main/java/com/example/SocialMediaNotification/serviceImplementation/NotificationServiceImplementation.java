package com.example.SocialMediaNotification.serviceImplementation;

import com.example.SocialMediaNotification.dto.LikesDto;
import com.example.SocialMediaNotification.enums.NotificationType;
import com.example.SocialMediaNotification.model.Notification;
import com.example.SocialMediaNotification.repo.NotificationRepo;
import com.example.SocialMediaPost.dto.PostDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImplementation {

    @Autowired
    private NotificationRepo notificationRepo;


    @KafkaListener(topics = "like-topic", groupId = "notification-group")
    public void listenLikeEvent(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LikesDto likesDto = objectMapper.readValue(message, LikesDto.class);
            Long userId = likesDto.getUserId();
            Long postId = likesDto.getPostId();
            Long timestampCreated = likesDto.getTimestampCreated();
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setPostId(postId);
            notification.setNotifiedTime(timestampCreated);
            notification.setNotificationType(NotificationType.LIKE);
            notificationRepo.save(notification);
            System.out.println("Notification saved: User " + userId + " liked Post " + postId);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing message: " + message);
        }
    }

    @KafkaListener(topics = "post-topic", groupId = "notification-group")
    public void listenPostEvent(String message) {
        try {
            System.out.println("Received message: " + message);
            ObjectMapper objectMapper = new ObjectMapper();
            PostDto postDto = objectMapper.readValue(message, PostDto.class);
            if (postDto.getUserId() == null || postDto.getTimestampCreated() == null) {
                System.err.println("Invalid PostDto data: Missing required fields");
                return;
            }
            Long userId = postDto.getUserId();
            Long postId = postDto.getId();
            Long timestampCreated = postDto.getTimestampCreated();
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setPostId(postId);
            notification.setNotifiedTime(timestampCreated);
            notification.setNotificationType(NotificationType.POST);
            notificationRepo.save(notification);
            System.out.println("Notification created and saved: " + notification);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON message: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "follow-topic", groupId = "notification-group")
    public void listenFollowEvent(String message){
        try {
            System.out.println(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @KafkaListener(topics = "comment-topic",groupId = "notification-group")
    public void listenCommentEvent(String message){
        try{
            System.out.println(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
