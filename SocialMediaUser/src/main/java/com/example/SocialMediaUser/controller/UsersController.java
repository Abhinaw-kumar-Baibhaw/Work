package com.example.SocialMediaUser.controller;

import com.example.SocialMediaUser.dto.UserDTO;
import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody Users users) {
        return usersService.createUser(users);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        return usersService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        return usersService.findById(id);
    }
}