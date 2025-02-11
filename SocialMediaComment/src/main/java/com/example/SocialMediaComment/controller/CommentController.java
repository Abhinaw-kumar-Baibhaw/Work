package com.example.SocialMediaComment.controller;


import com.example.SocialMediaComment.model.Comment;
import com.example.SocialMediaComment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Comment> create(@RequestBody Comment comment) {
        Comment createdComment = commentService.create(comment).getBody();
        return ResponseEntity.status(201).body(createdComment);
    }


    @GetMapping("/getById/{postId}")
    public ResponseEntity<List<Comment>> getById(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.getById(postId).getBody();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }

}
