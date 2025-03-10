package com.example.SocialMediaComment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SocialMediaCommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaCommentApplication.class, args);
	}

}
