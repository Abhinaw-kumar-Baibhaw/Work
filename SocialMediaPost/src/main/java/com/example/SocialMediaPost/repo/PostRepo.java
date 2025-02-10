package com.example.SocialMediaPost.repo;

import com.example.SocialMediaPost.dto.Comment;
import com.example.SocialMediaPost.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
