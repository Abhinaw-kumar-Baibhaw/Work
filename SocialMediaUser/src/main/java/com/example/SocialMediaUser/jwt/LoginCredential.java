package com.example.SocialMediaUser.jwt;

import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.repo.UsersRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> authenticateUser(@RequestBody Users user, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            String email = user.getEmail();
            Users authenticatedUser = usersRepo.findByEmail(email);
            if (authenticatedUser != null) {
                session.setAttribute("user", authenticatedUser);
                String role = authenticatedUser.getRole();
                String token = jwtService.generateToken(email, role);
                return ResponseEntity.ok(new LoginResponse(token, authenticatedUser));
            }
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during authentication");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully.");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("No active session found. Please log in.");
    }

}
