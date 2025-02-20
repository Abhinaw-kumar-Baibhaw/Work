package com.example.SocialMediaFollow.repo;

import com.example.SocialMediaFollow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<Follow,Long> {
    List<Follow> findByFollowedId(Long followedId);

    List<Follow> findByFollowerId(Long followerId);

}
