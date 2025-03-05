package com.example.SocialMediaApiGateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;



@Service
public class JwtUtil {


    private static final byte[] secretKey =("qXbFtb5dN8HG9j6+PhD7LDBb02fW3sP6M5LkW8nA0K8j34fB/3g7Wx2site").getBytes();

    public void validateToken(String token){
      Jwts.parserBuilder().setSigningKey((getSignKey())).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey);
    }

    public String extractUserName(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }



}
