package com.example.SocialMediaPost.dto;

public class Comment {


    private Long id;

    private Long userId;

    private Long postId;

    private String commentText;

    private Long timestamp;

    public Comment(){

    }

    public Comment(Long id, Long userId, Long postId, String commentText, Long timestamp) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


}
