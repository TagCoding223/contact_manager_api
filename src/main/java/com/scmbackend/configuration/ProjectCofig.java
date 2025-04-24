package com.scmbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class ProjectCofig {

    @Value("${config.cloudinary.cloud.key}")
    private String key;
    @Value("${config.cloudinary.cloud.secret}")
    private String secret;
    @Value("${config.cloudinary.cloud.cloudinaryName}")
    private String cloudinaryName;

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name",cloudinaryName,
                "api_key",key,
                "api_secret",secret
            )
        );
    }

    
}
