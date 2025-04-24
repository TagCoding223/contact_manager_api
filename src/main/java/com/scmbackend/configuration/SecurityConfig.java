package com.scmbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // httpSecurity.csrf(AbstractHttpConfigurer::disable); // csrf use when our frontend genreated from backend
        httpSecurity
            .cors() // Enable CORS
            .and()
            .csrf().disable()
            .httpBasic();
        
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST,"/api/v1/roles").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll(); // is it safe (i think if someone get the user details then their decryt the password and perform hacking (solution may be -> when try to get user details for login just check it backend side )) , i'm think too much i change the reposnce they get by user it time of login
            // auth.requestMatchers("/api/v1/**").hasAnyRole("ADMIN","GUEST");
            auth.requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll(); // it is only allow to create a user 
            // auth.requestMatchers("/api/v1/**").permitAll();
            auth.requestMatchers("/api/v1/**").authenticated();
            auth.anyRequest().permitAll();
        });

        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // it execute file before authentication it use to check user cridental comes or not like tokens (entry point filter)

        
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
