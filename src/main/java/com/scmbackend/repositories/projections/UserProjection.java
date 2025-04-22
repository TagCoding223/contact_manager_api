package com.scmbackend.repositories.projections;

import org.springframework.data.rest.core.config.Projection;

import com.scmbackend.entities.User;

@Projection(name = "user-projection",types = {User.class})
public interface UserProjection {
    String getName();
    String getEmail();
    String getId();
    String getAbout();
    String getPhoneNumber();
}
