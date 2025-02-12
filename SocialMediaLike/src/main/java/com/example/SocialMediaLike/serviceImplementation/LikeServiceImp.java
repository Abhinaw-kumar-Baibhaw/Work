package com.example.SocialMediaLike.serviceImplementation;

import com.example.SocialMediaLike.model.Likes;
import com.example.SocialMediaLike.repo.LikeRepo;
import com.example.SocialMediaLike.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class LikeServiceImp implements LikesService {

    private final LikeRepo likesRepository;
    private final RestTemplate restTemplate;


    @Autowired
    public LikeServiceImp(LikeRepo likesRepository,RestTemplate restTemplate) {
        this.likesRepository = likesRepository;
        this.restTemplate = restTemplate;

    }

    @Override
    @Cacheable(value = "likes", key = "#postId")
    public ResponseEntity<Long> countLikesByPostId(Long postId) {
        Long count = (long) likesRepository.countByPostId(postId);
        if (count == null || count == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0L);
        }
        return ResponseEntity.ok(count);
    }

    @Override
    public ResponseEntity<Likes> create(Likes likes) {
        Likes savedLike = likesRepository.save(likes);
        Long postId = likes.getPostId();
        Long userId = likes.getUserId();
        try {
            String notificationUrl = "http://SOCIALMEDIANOTIFICATION/notifications/like?postId=" + postId + "&userId=" + userId;
            restTemplate.postForEntity(notificationUrl, null, String.class);
        } catch (Exception e) {
            System.err.println("Failed to call Notification Service: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLike);
    }
}