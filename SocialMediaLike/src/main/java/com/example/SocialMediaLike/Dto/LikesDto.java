package com.example.SocialMediaLike.Dto;


public class LikesDto {


    private Long id;

    private Long userId;

    private Long postId;

    private Long timestampCreated;

    public LikesDto(){

    }

    public LikesDto(Long id, Long userId, Long postId, Long timestampCreated) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.timestampCreated = timestampCreated;
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

    public Long getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Long timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

}



