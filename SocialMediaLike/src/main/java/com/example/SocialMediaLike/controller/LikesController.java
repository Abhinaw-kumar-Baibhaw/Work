package com.example.SocialMediaLike.controller;

import com.example.SocialMediaLike.Dto.LikesDto;
import com.example.SocialMediaLike.model.Likes;
import com.example.SocialMediaLike.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;


    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/create")
    public ResponseEntity<Likes> create(@RequestBody LikesDto likes) {
        if (likes == null || likes.getPostId() == null || likes.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Likes createdLike = likesService.create(likes).getBody();
        if (createdLike != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLike);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long postId) {
        if (postId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ResponseEntity<Long> response = likesService.countLikesByPostId(postId);
        if (response.getBody() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0L);
        }
        return response;
    }
}
