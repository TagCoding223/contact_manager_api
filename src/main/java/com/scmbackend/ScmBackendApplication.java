package com.scmbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scmbackend.entities.Role;
import com.scmbackend.repositories.RoleRepositry;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ScmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScmBackendApplication.class, args);
	}
	private final RoleRepositry roleRepositry;

    public ScmBackendApplication(RoleRepositry roleRepositry) {
        this.roleRepositry = roleRepositry;
    }

    @PostConstruct
    public void initRoles() {
        if (!roleRepositry.existsById(3L)) {
            Role userRole = new Role(3L, "ROLE_USER");
            roleRepositry.save(userRole);
        }
    }

}
