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

}

