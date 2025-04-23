package com.scmbackend.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.scmbackend.services.JwtService;
import com.scmbackend.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private JwtService jwtService;

    private UserService userService;

    

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        String authHeader = request.getHeader("Authorization");
        if (authHeader!=null && authHeader.startsWith("Bearer ")) {
            // token like this : Bearer3435434dd
            String token = authHeader.substring(7); // remove Bearer , it use to identify

            // validate token
            if (jwtService.validateToken(token)) {
                String usernameFromToken = jwtService.getUsernameFromToken(token);
                UserDetails userDetails = userService.loadUserByUsername(usernameFromToken);

                if (SecurityContextHolder.getContext().getAuthentication()==null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
