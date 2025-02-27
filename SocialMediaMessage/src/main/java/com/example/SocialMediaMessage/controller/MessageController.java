package com.example.SocialMediaMessage.controller;

import com.example.SocialMediaMessage.Dto.MessageDto;
import com.example.SocialMediaMessage.model.Message;
import com.example.SocialMediaMessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/sent")
    public ResponseEntity<MessageDto> sentMessage(@RequestBody Message message){
        return messageService.sendMessage(message);
    }
}
