package com.example.SocialMediaUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SocialMediaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaUserApplication.class, args);
	}

}
