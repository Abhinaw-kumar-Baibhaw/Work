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
    public Comment create(@RequestBody Comment comment){
      return commentService.create(comment);
    }

    @GetMapping("/getById/{postId}")
    public ResponseEntity<List<Comment>> getById(@PathVariable("postId") Long postId){
        return commentService.getById(postId);
    }
}
