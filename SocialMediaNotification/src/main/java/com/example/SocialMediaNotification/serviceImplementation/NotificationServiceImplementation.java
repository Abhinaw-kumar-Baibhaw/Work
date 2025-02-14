package com.example.SocialMediaNotification.serviceImplementation;
import com.example.SocialMediaNotification.dto.LikesDto;
import com.example.SocialMediaNotification.enums.NotificationType;
import com.example.SocialMediaNotification.model.Notification;
import com.example.SocialMediaNotification.repo.NotificationRepo;
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
}
