package com.example.SocialMediaLike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SocialMediaLikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaLikeApplication.class, args);
	}

}
