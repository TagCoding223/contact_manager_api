package com.scmbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scmbackend.configuration.AppConstants;
import com.scmbackend.entities.Role;
import com.scmbackend.repositories.RoleRepositry;
import com.scmbackend.services.HelperSingletonService;
import com.scmbackend.services.ImageUploadService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ScmBackendApplication {


    // TODO: when we use jwt then user automatically logout after token is expired

	public static void main(String[] args) {
		SpringApplication.run(ScmBackendApplication.class, args);
	}
	private final RoleRepositry roleRepositry;

    public ScmBackendApplication(RoleRepositry roleRepositry) {
        this.roleRepositry = roleRepositry;
    }

    @PostConstruct
    public void initRoles() {
        if (!roleRepositry.existsById(2L)) {
            Role userRole = new Role(2L, AppConstants.ROLE_USER);
            roleRepositry.save(userRole);
        }
        if (!roleRepositry.existsById(1L)) {
            Role userRole = new Role(1L, AppConstants.ROLE_ADMIN);
            roleRepositry.save(userRole);
        }
    }

    @Autowired
    public void configureService(ImageUploadService imageUploadService){
        HelperSingletonService.setImageUploadService(imageUploadService);
    }
}
