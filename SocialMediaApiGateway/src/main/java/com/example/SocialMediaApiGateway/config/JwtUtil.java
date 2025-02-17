package com.example.SocialMediaApiGateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;



@Service
public class
JwtUtil {


    private static final byte[] secretKey =("qXbFtb5dN8HG9j6+PhD7LDBb02fW3sP6M5LkW8nA0K8j34fB/3g7Wx2site").getBytes();

    public void validateToken(String token){
      Jwts.parserBuilder().setSigningKey((getSignKey())).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey);
    }


}
