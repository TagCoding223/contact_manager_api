package com.scmbackend.repositories;

import com.scmbackend.entities.Contact;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

// @RepositoryRestResource(exported = false) // this annotation allow to block this repository as an api (block to expose this repository as an api) try it by uncomment it and hit a request by thunder client
// @RepositoryRestResource(path = "user-contacts", collectionResourceRel = "user-contacts") // custom api endpoint , second attribute change the relation name to see changes visit "base_endpoint" in thunder client they hit "http://localhost:8080"
public interface ContactRepository extends JpaRepository<Contact,String> {
    // @RestResource(exported = false)
    @RestResource(path = "by-email")
    List<Contact> findByEmailContainingIgnoreCase(@Param("email") String email, Pageable pageable); // this is automatically expose, and default url "/contacts/search/email_?email=abc@gmail.com"
    // @RestResource(path = "by-phone", rel = "by-phone")
    List<Contact> findByPhoneNumber(@Param("phone") String phoneNumber, Pageable pageable);

    @RestResource(path = "by-name")
    List<Contact> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
