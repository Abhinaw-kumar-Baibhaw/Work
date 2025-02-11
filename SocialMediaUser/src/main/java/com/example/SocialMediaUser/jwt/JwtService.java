package com.example.SocialMediaUser.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    public static final  long TOKEN_VALIDITY = 60 *60 * 1000;

    private static final byte[] secretKey =("qXbFtb5dN8HG9j6+PhD7LDBb02fW3sP6M5LkW8nA0K8j34fB/3g7Wx2site").getBytes();

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role","ROLE_"+ role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getSignKey())
                .compact();
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

    public boolean validateToken(String token, UserDetails userDetails){
        Claims claimsDetails = getAllClaimsFromToken(token);
        String userName = claimsDetails.getSubject();
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        Claims claims=getAllClaimsFromToken(token);
        return claims.getExpiration();
    }
}
