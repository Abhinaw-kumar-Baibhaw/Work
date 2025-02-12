package com.example.SocialMediaNotification.serviceImplementation;

import com.example.SocialMediaNotification.dto.Post;
import com.example.SocialMediaNotification.enums.NotificationType;
import com.example.SocialMediaNotification.exception.PostNotFoundException;
import com.example.SocialMediaNotification.model.Notification;
import com.example.SocialMediaNotification.repo.NotificationRepo;

import com.example.SocialMediaNotification.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImplementation {

    private final NotificationRepo notificationRepo;
    private final PostRepo postRepository;

    @Autowired
    public NotificationServiceImplementation(NotificationRepo notificationRepo, PostRepo postRepository) {
        this.notificationRepo = notificationRepo;
        this.postRepository = postRepository;
    }

    public ResponseEntity<String> createLikeNotification(Long postId, Long userId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
            Long ownerId = post.getUserId();
            Notification notification = new Notification();
            notification.setUserId(ownerId);
            notification.setMessage("Your post was liked by user " + userId);
            notification.setTimestamp(LocalDateTime.now());
            notification.setNotificationType(NotificationType.LIKE);
            notificationRepo.save(notification);
            return ResponseEntity.status(HttpStatus.CREATED).body("Like notification created successfully.");
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create like notification: " + e.getMessage());
        }
    }
}
