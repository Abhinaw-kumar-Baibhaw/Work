package com.example.SocialMediaNotification.controller;

import com.example.SocialMediaNotification.serviceImplementation.NotificationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationServiceImplementation notificationService;

    @Autowired
    public NotificationController(NotificationServiceImplementation notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/like")
    public ResponseEntity<String> createLikeNotification(@RequestParam Long postId, @RequestParam Long userId) {
        try {
            notificationService.createLikeNotification(postId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Like notification created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create like notification: " + e.getMessage());
        }
    }
}

