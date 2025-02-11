package com.example.SocialMediaUser.service;

import com.example.SocialMediaUser.dto.UserDTO;
import com.example.SocialMediaUser.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UsersService {

    ResponseEntity<UserDTO> createUser(Users users);

    ResponseEntity<List<UserDTO>> getAllUsers();

    ResponseEntity<UserDTO> findById(Long id);
}
