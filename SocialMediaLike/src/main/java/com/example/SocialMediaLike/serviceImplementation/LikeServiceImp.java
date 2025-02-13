package com.example.SocialMediaLike.serviceImplementation;

import com.example.SocialMediaLike.model.Likes;
import com.example.SocialMediaLike.repo.LikeRepo;
import com.example.SocialMediaLike.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class LikeServiceImp implements LikesService {

    private final LikeRepo likesRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    public LikeServiceImp(LikeRepo likesRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.likesRepository = likesRepository;
        this.kafkaTemplate = kafkaTemplate;
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
        Optional<Likes> existingLike = likesRepository.findByUserIdAndPostId(likes.getUserId(), likes.getPostId());
        if (existingLike.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(existingLike.get());
        }
        Likes savedLike = likesRepository.save(likes);
        String message = "User " + likes.getUserId() + " liked post " + likes.getPostId();
        kafkaTemplate.send("like-topic", message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLike);
    }
}