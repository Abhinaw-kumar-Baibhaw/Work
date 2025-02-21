package com.example.SocialMediaFollow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;

    private Long followedId;

    private LocalDateTime createdAt;

    @Transient
    private Users users;

    private int followerCount;

    public Follow() {
    }

    public Follow(Long id, Long followerId, Long followedId, LocalDateTime createdAt, Users users, int followerCount) {
        this.id = id;
        this.followerId = followerId;
        this.followedId = followedId;
        this.createdAt = createdAt;
        this.users = users;
        this.followerCount = followerCount;
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

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    @Override
    public String toString() {
        return "FollowDto{" +
                "id=" + id +
                ", followerId=" + followerId +
                ", followedId=" + followedId +
                ", createdAt=" + createdAt +
                ", users=" + users +
                ", followerCount=" + followerCount +
                '}';
    }
}
