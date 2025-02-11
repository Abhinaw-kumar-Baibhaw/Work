package com.example.SocialMediaLike.repo;

import com.example.SocialMediaLike.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<Likes,Long> {
    int countByPostId(Long postId);

}
