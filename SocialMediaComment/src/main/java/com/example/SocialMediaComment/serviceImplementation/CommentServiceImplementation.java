package com.example.SocialMediaComment.serviceImplementation;

import com.example.SocialMediaComment.model.Comment;
import com.example.SocialMediaComment.repo.CommentRepo;
import com.example.SocialMediaComment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Override
    public Comment create(Comment comment) {
        return commentRepo.save(comment);
    }


    @Override
    public ResponseEntity<List<Comment>> getById(Long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        if (!comments.isEmpty()) {
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
