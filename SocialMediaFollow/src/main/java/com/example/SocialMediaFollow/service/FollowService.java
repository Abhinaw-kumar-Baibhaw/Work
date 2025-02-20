package com.example.SocialMediaFollow.service;

import com.example.SocialMediaFollow.dto.FollowDto;
import com.example.SocialMediaFollow.model.Follow;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowService {

    ResponseEntity<FollowDto> follow(Follow follow);

    ResponseEntity<FollowDto> unfollow(String follower);

    ResponseEntity<List<FollowDto>> getFollowers(Long followedId);

     ResponseEntity<List<FollowDto>> getFollowing(Long followerId);

    }
