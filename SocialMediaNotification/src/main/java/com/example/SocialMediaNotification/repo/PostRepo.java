package com.example.SocialMediaNotification.repo;

import com.example.SocialMediaNotification.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
