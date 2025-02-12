package com.example.SocialMediaUser.controller;

import com.example.SocialMediaUser.dto.UserDTO;
import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (users == null || users.getName()== null || users.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ResponseEntity<UserDTO> response = usersService.createUser(users);
        return response;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = usersService.getAllUsers().getBody();
        if (users == null || users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ResponseEntity<UserDTO> response = usersService.findById(id);
        if (response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }
}
