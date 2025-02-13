package com.example.SocialMediaNotification.serviceImplementation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImplementation {


    @KafkaListener(topics = "like-topic", groupId = "notification-group")
    public void listenLikeEvent(String message) {
        System.out.println("Notification: " + message);
    }
}
