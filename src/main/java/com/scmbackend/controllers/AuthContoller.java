package com.scmbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmbackend.dto.AuthRequest;
import com.scmbackend.dto.JwtResponse;
import com.scmbackend.dto.RefreshTokenRequest;
import com.scmbackend.entities.User;
import com.scmbackend.repositories.UserRepository;
import com.scmbackend.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthContoller {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthContoller(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
            JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userRepository=userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String accessToken = jwtService.generateToken(request.getEmail(), true);
        String refreshToken = jwtService.generateToken(request.getEmail(), false);
        User user = userRepository.findByEmail(request.getEmail()).get();

        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, user);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request){
        if (jwtService.validateToken(request.refreshToken())) {
            String usernameFromToken = jwtService.getUsernameFromToken(request.refreshToken());

            // apply many validations here
            String accessToken = jwtService.generateToken(usernameFromToken, true);

            String refreshToken = jwtService.generateToken(request.refreshToken(), false);

            User user = userRepository.findByEmail(usernameFromToken).get();

        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, user);

        return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
    }
}
