package com.scmbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.scmbackend.entities.Contact;
import com.scmbackend.entities.User;

@Configuration
public class RestConfig {

    // we need to use below method for this project because .properties file value execute prefectly
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(){
        return new RepositoryRestConfigurer() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry corsRegistry){
                config.setBasePath(AppConstants.REST_BASE_PATH);
                config.setDefaultPageSize(AppConstants.PAGE_SIZE);
                config.setDefaultMediaType(MediaType.APPLICATION_JSON);
                config.exposeIdsFor(User.class,Contact.class);
                // config.getProjectionConfiguration().addProjection(UserProjection.class); // use when projecject folder iutside of repositories folder
            }
        };
    }
}
