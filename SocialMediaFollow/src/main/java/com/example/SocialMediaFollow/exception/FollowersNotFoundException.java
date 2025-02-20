package com.example.SocialMediaFollow.exception;


public class FollowersNotFoundException extends RuntimeException{
    public FollowersNotFoundException(String message){
        super(message);
    }
}
