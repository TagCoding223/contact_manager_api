package com.scmbackend.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.scmbackend.entities.Role;
import com.scmbackend.entities.User;
import com.scmbackend.repositories.UserRepository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    public static final String SECRET_KEY = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";
    public static final long ACCESS_TOKEN_EXPIRATION = 15*60*1000; // 15 min 
    public static final long REFESH_TOKEN_EXPIRATION = 48*60*60*1000; // 48 min 

    @Autowired
    private UserRepository userRepository;
    // generate token
    // public String generateToken(String username, boolean isAccessToken){
    //     long expiration = isAccessToken ? ACCESS_TOKEN_EXPIRATION : REFESH_TOKEN_EXPIRATION;

    //     return Jwts.builder()
    //     .setSubject(username)
    //     .setIssuedAt(new Date())
    //     .setExpiration(new Date(System.currentTimeMillis()+expiration))
    //     .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
    //     .compact();    
        
    // }
    public String generateToken(String username, boolean isAccessToken) {
        long expiration = isAccessToken ? ACCESS_TOKEN_EXPIRATION : REFESH_TOKEN_EXPIRATION;
    
        // Fetch user details from the database
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
    
        // Add roles to claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream()
                .map(Role::getRole) // Assuming Role entity has a `getName()` method
                .collect(Collectors.toList()));
    
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // get name from token
    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    //validate
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
