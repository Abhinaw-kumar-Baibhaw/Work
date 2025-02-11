package com.example.SocialMediaComment.service;

import com.example.SocialMediaComment.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    ResponseEntity<Comment> create(Comment comment);

    ResponseEntity<List<Comment>> getById(Long postId);

}
