package com.scmbackend.events;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.scmbackend.entities.Contact;


@Component
@RepositoryEventHandler
public class ContactEventHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @HandleBeforeCreate
    public void handleBeforeCreate(Contact contact) {
        logger.info("Contact handler before created(going to create contact object).");
        contact.setId(UUID.randomUUID().toString());
        // user.setPassword();
    }

    @HandleBeforeSave
    public void handleBeforeSave(Contact contact) {
        logger.debug("Contact before save handler method started."); //before save handle method running before save at db.
    }
}
