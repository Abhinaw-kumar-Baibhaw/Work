package com.example.SocialMediaMessage.serviceImplementation;

import com.example.SocialMediaMessage.Dto.MessageDto;
import com.example.SocialMediaMessage.enums.MessageStatus;
import com.example.SocialMediaMessage.model.Message;
import com.example.SocialMediaMessage.model.Users;
import com.example.SocialMediaMessage.repo.MessageRepo;
import com.example.SocialMediaMessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImplementation implements MessageService {


    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private RestTemplate restTemplate;

    public MessageDto convertToDto(Message message){
        return new MessageDto(
                message.getId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.getTimestamp(),
                message.getStatus()
        );
    }

    @Override
    public ResponseEntity<MessageDto> sendMessage(Message message) {
        Message message1 = new Message();
        message1.setSenderId(message.getSenderId());
        message1.setReceiverId(message.getReceiverId());
        message1.setContent(message.getContent());
        message1.setTimestamp(LocalDateTime.now());
        message1.setStatus(MessageStatus.SENT);
        Message savedMessage = messageRepo.save(message1);
        return new ResponseEntity<>(convertToDto(savedMessage), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<MessageDto>> getInfo(Long senderId) {
        String url = "http://SOCIALMEDIAUSER/user/getById/" + senderId;
        Users user = restTemplate.getForObject(url, Users.class);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Message> messages = messageRepo.findBySender(user);
        List<MessageDto> messageDtos = messages.stream()
                .map(message -> new MessageDto(message))
                .collect(Collectors.toList());
        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
    }


}
