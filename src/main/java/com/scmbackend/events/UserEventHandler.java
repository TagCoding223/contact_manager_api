package com.scmbackend.events;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scmbackend.entities.Role;
import com.scmbackend.entities.User;
import com.scmbackend.repositories.RoleRepositry;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepositry roleRepositry;

    @HandleBeforeCreate
    public void handleBeforeCreate(User user) {
        // call when user create first time
        logger.debug("User handler before created(going to create user object).");
        user.setId(UUID.randomUUID().toString());
        Role role = roleRepositry.findById(3L).orElseThrow(() -> new RuntimeException("Role not found."));
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // i comment it because i need to encrpty password
                                                                      // when new user create and update that why i use
                                                                      // this statement in before save , if i'am user in
                                                                      // both method then normal password -> encrypted
                                                                      // password -> encrypted password
    }

    @HandleBeforeSave
    public void handleBeforeSave(User user) {
        logger.debug("User before save handler method started."); // before save handle method running before save at
                                                                  // db.
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Ensure roles are managed entities
        Set<Role> managedRoles = new LinkedHashSet<>();
        for (Role role : user.getRoles()) {
            Role managedRole = roleRepositry.findById(role.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + role.getId()));
            managedRoles.add(managedRole);
        }
        user.setRoles(managedRoles);

        // Encode the password if it has been updated
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

}
