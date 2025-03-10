package com.example.SocialMediaMessage.repo;

import com.example.SocialMediaMessage.model.Message;
import com.example.SocialMediaMessage.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findBySenderId(Long senderId);
}
