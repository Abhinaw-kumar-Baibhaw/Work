package com.example.SocialMediaLike.repo;

import com.example.SocialMediaLike.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Likes,Long> {
    int countByPostId(Long postId);

    Optional<Likes> findByUserIdAndPostId(Long userId, Long postId);
}
