package com.example.SocialMediaFollow.dto;

import com.example.SocialMediaFollow.model.Users;
import jakarta.persistence.*;

import java.time.LocalDateTime;




public class FollowDto {

    private Long id;

    private Long followerId;

    private Long followedId;

    private LocalDateTime createdAt;

    @Transient
    private Users users;

    public FollowDto(){

    }

    public FollowDto(Long id, Long followerId, Long followedId, LocalDateTime createdAt, Users users) {
        this.id = id;
        this.followerId = followerId;
        this.followedId = followedId;
        this.createdAt = createdAt;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }


}
