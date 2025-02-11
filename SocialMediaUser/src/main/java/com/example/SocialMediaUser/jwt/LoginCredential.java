package com.example.SocialMediaUser.jwt;

import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class LoginCredential {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Users user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String email = user.getEmail();
        Users user1 = usersRepo.findByEmail(email);
        String role =  user1.getRole();
        String token = jwtService.generateToken(email, role);
        return ResponseEntity.ok(token);
    }
}
