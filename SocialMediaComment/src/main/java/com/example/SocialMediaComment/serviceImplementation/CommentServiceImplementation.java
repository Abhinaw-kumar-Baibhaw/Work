package com.example.SocialMediaComment.serviceImplementation;

import com.example.SocialMediaComment.model.Comment;
import com.example.SocialMediaComment.repo.CommentRepo;
import com.example.SocialMediaComment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImplementation.class);

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public ResponseEntity<Comment> create(Comment comment) {
        logger.info("Creating a new comment for post ID: {}", comment.getPostId());
        Comment savedComment = commentRepo.save(comment);
        String message = comment.getUserId() +" commented on you post";
        kafkaTemplate.send("comment-topic",message);
        logger.info("Comment created successfully with ID: {}", savedComment.getId());
        return ResponseEntity.status(201).body(savedComment);
    }


    @Override
    @Cacheable(value = "comment", key = "#postId")
    public ResponseEntity<List<Comment>> getById(Long postId) {
        logger.info("Fetching comments for post ID: {}", postId);
        List<Comment> comments = commentRepo.findByPostId(postId);

        if (!comments.isEmpty()) {
            logger.info("Found {} comments for post ID: {}", comments.size(), postId);
            return ResponseEntity.ok(comments);
        } else {
            logger.warn("No comments found for post ID: {}", postId);
            return ResponseEntity.noContent().build();
        }
    }
}
