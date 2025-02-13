package com.example.SocialMediaNotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableKafka
public class SocialMediaNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaNotificationApplication.class, args);
	}

}
