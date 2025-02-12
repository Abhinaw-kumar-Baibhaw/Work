package com.example.SocialMediaNotification.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(String message){
        super(message);
    }

}
