package com.example.SocialMediaPost.dto;

public class PostDto {

    private Long id;
    private String content;
    private String imageUrl;
    private Long userId;
    private Long timestampCreated;
    private Long timestampUpdated;
    private Users user; // User details
    private Long likeCount; // Like count
    private Long commentCount; // Comment count

    // Constructor including all necessary fields
    public PostDto(Long id, String content, String imageUrl, Long userId, Long timestampCreated, Long timestampUpdated, Users user, Long likeCount, Long commentCount) {
        this.id = id;
        this.content = content;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.timestampCreated = timestampCreated;
        this.timestampUpdated = timestampUpdated;
        this.user = user;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Long timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Long getTimestampUpdated() {
        return timestampUpdated;
    }

    public void setTimestampUpdated(Long timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
