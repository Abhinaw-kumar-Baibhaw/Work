package com.example.SocialMediaLike.serviceImplementation;

import com.example.SocialMediaLike.Dto.LikesDto;
import com.example.SocialMediaLike.model.Likes;
import com.example.SocialMediaLike.repo.LikeRepo;
import com.example.SocialMediaLike.service.LikesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImp implements LikesService {

    private final LikeRepo likesRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final Logger log = LoggerFactory.getLogger(LikeServiceImp.class);

    @Autowired
    public LikeServiceImp(LikeRepo likesRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.likesRepository = likesRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public LikesDto convertToDto(Likes likes) {
        return new LikesDto(likes.getId(), likes.getUserId(), likes.getPostId(), likes.getTimestampCreated());
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
    public ResponseEntity<Likes> create(LikesDto likesDto) {
        try {
            Optional<Likes> existingLike = likesRepository.findByUserIdAndPostId(likesDto.getUserId(), likesDto.getPostId());
            if (existingLike.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(existingLike.get());
            }
            Likes like = new Likes();
            like.setUserId(likesDto.getUserId());
            like.setPostId(likesDto.getPostId());
            like.setTimestampCreated(System.currentTimeMillis());
            Likes savedLike = likesRepository.save(like);
            LikesDto savedLikesDto = convertToDto(savedLike);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(savedLikesDto);
            kafkaTemplate.send("like-topic", jsonMessage);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLike);
        } catch (Exception e) {
            log.error("Error creating like", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
