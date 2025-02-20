package com.example.SocialMediaFollow.controller;

import com.example.SocialMediaFollow.dto.FollowDto;
import com.example.SocialMediaFollow.model.Follow;
import com.example.SocialMediaFollow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/followed")
    public ResponseEntity<FollowDto> followUser(@RequestBody Follow follow) {
        return followService.follow(follow);
    }


    @GetMapping("/followers/{followedId}")
    public ResponseEntity<List<FollowDto>> getFollowers(@PathVariable Long followedId) {
        return followService.getFollowers(followedId);
    }

    @GetMapping("/following/{followerId}")
    public ResponseEntity<List<FollowDto>> getFollowing(@PathVariable Long followerId) {
        return followService.getFollowing(followerId);
    }
}
