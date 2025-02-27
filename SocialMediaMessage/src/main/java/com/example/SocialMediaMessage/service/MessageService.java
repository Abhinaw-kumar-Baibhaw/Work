package com.example.SocialMediaMessage.service;

import com.example.SocialMediaMessage.Dto.MessageDto;
import com.example.SocialMediaMessage.model.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {
     ResponseEntity<MessageDto> sendMessage(Message message);

     ResponseEntity<List<MessageDto>> getInfo(Long senderId);
}
