package com.example.SocialMediaLike.service;

import com.example.SocialMediaLike.model.Likes;

import java.util.List;


public interface LikesService {

    long countLikesByPostId(Long postId);

    Likes create(Likes likes);

}

