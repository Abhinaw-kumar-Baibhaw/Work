package com.example.SocialMediaLike.service;

import com.example.SocialMediaLike.Dto.LikesDto;
import com.example.SocialMediaLike.model.Likes;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface LikesService {

    ResponseEntity<Long> countLikesByPostId(Long postId);

    ResponseEntity<Likes> create(LikesDto likesDto);

}

