package com.scmbackend.events;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.scmbackend.entities.User;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @HandleBeforeCreate
    public void handleBeforeCreate(User user) {
        logger.debug("User handler before created(going to create user object).");
        user.setId(UUID.randomUUID().toString());
        // user.setPassword();
    }

    @HandleBeforeSave
    public void handleBeforeSave(User user) {
        logger.debug("User before save handler method started."); //before save handle method running before save at db.
    }
}
