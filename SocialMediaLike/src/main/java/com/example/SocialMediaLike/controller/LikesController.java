package com.example.SocialMediaLike.controller;

import com.example.SocialMediaLike.model.Likes;
import com.example.SocialMediaLike.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Likes create(@RequestBody Likes likes){
        return likesService.create(likes);
    }

    @GetMapping("/count/{postId}")
    public long getLikesCount(@PathVariable Long postId) {
        return likesService.countLikesByPostId(postId);
    }
}

