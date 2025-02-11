package com.example.SocialMediaLike.serviceImplementation;

import com.example.SocialMediaLike.model.Likes;
import com.example.SocialMediaLike.repo.LikeRepo;
import com.example.SocialMediaLike.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LikeServiceImp implements LikesService {

    private final LikeRepo likesRepository;

    @Autowired
    public LikeServiceImp(LikeRepo likesRepository) {
        this.likesRepository = likesRepository;
    }

    @Cacheable(value = "comment", key = "#postId")
    public long countLikesByPostId(Long postId) {
        return likesRepository.countByPostId(postId);
    }

    @Override
    public Likes create(Likes likes) {
        return likesRepository.save(likes);
    }


}
